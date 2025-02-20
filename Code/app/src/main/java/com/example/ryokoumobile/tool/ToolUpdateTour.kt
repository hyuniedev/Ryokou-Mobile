package com.example.ryokoumobile.tool

import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import kotlin.random.Random

//fun toolUpdateTour() {
//    val lsTour = DataController.tourVM.uiState.value
//    for (tour in lsTour) {
//        tour.ticketLimit.numLimitTicket = Random.nextInt(10, 100)
//        FirebaseController.firestore.collection("tours").document(tour.id).set(tour)
//    }
//}