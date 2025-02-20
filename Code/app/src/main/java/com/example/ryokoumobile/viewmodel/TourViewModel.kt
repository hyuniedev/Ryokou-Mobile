package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.repository.Scenes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TourViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<List<Tour>>(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        LoadDataTour()
    }

    private fun LoadDataTour() {
        val lsTour = mutableListOf<Tour>()
        FirebaseController.firestore.collection("tours")
            .get()
            .addOnSuccessListener { result ->
                try {
                    for (doc in result) {
                        val tour = doc.toObject(Tour::class.java)
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
            .addOnFailureListener { e -> Log.e("HyuNie", "Error on $e") }
    }

    fun addRate(tour: Tour, rate: Rate) {
        val index = _uiState.value.indexOfFirst { tour.id == it.id }
        val lsTour = _uiState.value.toMutableList()
        lsTour[index].lsRate += rate
        FirebaseController.firestore.collection("rates").document(rate.id).set(rate)
        _uiState.update { lsTour.toList() }
    }

    fun incNumCurrentTicket(tour: Tour, booked: TourBooked) {
        val index = _uiState.value.indexOfFirst { tour.id == it.id }
        val lsTour = _uiState.value
        lsTour[index].ticketLimit.numCurrentTicket += booked.numPerson
        _uiState.update { lsTour }
        FirebaseController.firestore.collection("tours").document(tour.id).set(tour)
    }

    fun getIsFavoriteTour(tour: Tour): Boolean {
        return DataController.user.value?.lsFavoriteTour?.contains(
            tour.id
        ) ?: false
    }

    fun getTourFromID(idTour: String): Tour {
        return _uiState.value.first {
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