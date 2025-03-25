package com.example.ryokoumobile.tool

import android.util.Log
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Tour
import com.google.firebase.Timestamp
import kotlin.random.Random

//fun toolUpdateLimitTicketOfTour() {
//    val lsTour = DataController.tourVM.uiState.value
//    for (tour in lsTour) {
//        tour.ticketLimit.numLimitTicket = Random.nextInt(10, 100)
//        FirebaseController.firestore.collection("tours").document(tour.id).set(tour)
//    }
//}

fun toolUpdateStartTimeOfTour() {
    FirebaseController.firestore.collection("tours")
        .get()
        .addOnSuccessListener { result ->
            try {
                for (data in result) {
                    val tour = data.toObject(Tour::class.java)
                    tour.start = Timestamp.now()
                    tour.maintainTime = Random.nextInt(20, 50)
                    FirebaseController.firestore.collection("tours").document(data.id).set(tour)
                }
            } catch (e: Exception) {
                Log.e("HyuNie", "${e.message}")
            }
        }
}

fun toolUpdateIdCompany(tours: List<Tour>) {
    for (data in tours) {
        data.company = "FKVpiErwHIcEIqGzpVOduNqV5HH3"
        FirebaseController.firestore.collection("tours")
            .document(data.id).set(data)
    }
}