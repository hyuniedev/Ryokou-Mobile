package com.example.ryokoumobile.model.entity

import com.google.firebase.Timestamp

data class TourBooked(
    var id: String = "",
    var numPerson: Int = 0,
    var idTour: String = "",
    val bookedDay: Timestamp = Timestamp.now(),
    var startDay: Timestamp = Timestamp.now()
)
