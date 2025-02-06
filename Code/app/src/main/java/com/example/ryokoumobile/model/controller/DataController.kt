package com.example.ryokoumobile.model.controller

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.viewmodel.TourViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

object DataController {
    var user = MutableStateFlow<User?>(null)
    val tourVM = TourViewModel()

    var lsBookedTour = mutableStateListOf<TourBooked>()

    fun updateFavoriteTour(tour: Tour) {
        if (user.value == null) return
        val ls = user.value?.lsFavoriteTour?.toMutableList() ?: mutableListOf()
        if (ls.contains(tour.id)) {
            ls.remove(tour.id)
        } else {
            ls.add(tour.id)
        }
        user.update {
            it?.copy(lsFavoriteTour = ls)
        }
        FirebaseController.firestore
            .collection("users")
            .document(user.value?.id!!)
            .set(user.value!!)
    }

    fun updateBookedTour(tourBooked: TourBooked) {
        if (user.value == null) return
        // add booked tour to firebase
        val doc = FirebaseController.firestore.collection("bookedTours").document()
        tourBooked.id = doc.id
        tourBooked.userId = user.value!!.id!!
        FirebaseController.firestore.collection("bookedTours").document(tourBooked.id)
            .set(tourBooked)
        lsBookedTour.add(tourBooked)
    }

    fun loadInitDataBookedTour() {
        if (user.value == null) return

        lsBookedTour = mutableStateListOf()

        FirebaseController.firestore.collection("bookedTours")
            .whereEqualTo("userId", user.value!!.id!!).get().addOnSuccessListener { datas ->
                for (data in datas) {
                    lsBookedTour.add(data.toObject(TourBooked::class.java))
                }
            }.addOnFailureListener {
                Log.e("HyuNie", "Error on load Booked Tour: ${it.message}")
            }
    }
}