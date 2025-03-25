package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.controller.UserAnalytics
import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.enumClass.EVariationTicket
import com.example.ryokoumobile.model.repository.Scenes
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class TourViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<List<Tour>>(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            LoadDataTour()
        }
    }

    private suspend fun LoadDataTour() {
        val lsTour = mutableListOf<Tour>()
        val result = FirebaseController.firestore.collection("tours")
            .get().await()
        try {
            for (doc in result) {
                val tour = doc.toObject(Tour::class.java)
                //-------Check active tour------------
                val calendar = Calendar.getInstance().apply {
                    time = tour.start.toDate()
                    add(Calendar.DAY_OF_MONTH, tour.maintainTime)
                }
                val curTime = Timestamp.now().toDate()
                if (calendar.time.before(curTime) || tour.start.toDate().after(curTime))
                    continue
                //------------------------------------
                tour.id = doc.id
                FirebaseController.firestore.collection("rates")
                    .whereEqualTo("tourId", tour.id).get()
                    .addOnSuccessListener {
                        tour.lsRate = it.toObjects(Rate::class.java)
                    }
                lsTour.add(tour)
            }
        } catch (e: Exception) {
            Log.e("HyuNie", "Load a error tour: ${e.message}")
        }
        _uiState.update { lsTour }

    }

    fun addRate(tour: Tour, rate: Rate) {
        val index = _uiState.value.indexOfFirst { tour.id == it.id }
        val lsTour = _uiState.value.toMutableList()
        lsTour[index].lsRate += rate
        FirebaseController.firestore.collection("rates").document(rate.id).set(rate)
        _uiState.update { lsTour.toList() }
    }

    fun setNumCurrentTicket(
        tour: Tour,
        booked: TourBooked,
        eVariationTicket: EVariationTicket
    ) {
        val index = _uiState.value.indexOfFirst { tour.id == it.id }
        val lsTour = _uiState.value
        lsTour[index].ticketLimit.numCurrentTicket += if (eVariationTicket == EVariationTicket.INC) booked.numPerson else -booked.numPerson
        _uiState.update { lsTour }
        FirebaseController.firestore.collection("tours").document(tour.id).set(tour)
    }

    fun getIsFavoriteTour(tour: Tour): Boolean {
        return DataController.user.value?.lsFavoriteTour?.contains(
            tour.id
        ) ?: false
    }

    fun getTourFromID(idTour: String): Tour? {
        return _uiState.value.find {
            it.id == idTour
        }
    }

    fun navigationToTourDetail(navController: NavController, tour: Tour) {
        navController.navigate(
            Scenes.TourDetail.route.replace(
                "{tourId}",
                tour.id
            )
        )
    }

    fun navigationToTourPay(navController: NavController, tourBooked: TourBooked) {
        navController.navigate(
            Scenes.TourPay.route
                .replace("{numTicket}", tourBooked.numPerson.toString())
                .replace("{dayStart}", tourBooked.startDay.seconds.toString())
                .replace("{tourId}", tourBooked.tourId)
        )
    }

    fun getRate(tour: Tour): List<Rate> {
        val ls = _uiState.value.toMutableList()
        val index = ls.indexOfFirst { it.id == tour.id }
        return ls[index].lsRate
    }
}