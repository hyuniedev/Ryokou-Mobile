package com.example.ryokoumobile.model.entity

import com.example.ryokoumobile.model.controller.DataController
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class TourBooked(
    var id: String = "",
    var numPerson: Int = 0,
    var tourId: String = "",
    var userId: String = "",
    val bookedDay: Timestamp = Timestamp.now(),
    var startDay: Timestamp = Timestamp.now()
) {
    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun getTotalPay(): String {
        val tour = DataController.tourVM.getTourFromID(tourId)!!.copy()
        tour.cost = (tour.cost.replace(".", "").toInt() * numPerson).toString()
        return tour.getPriceWithFormatted()
    }

    fun getEndDay(): Date {
        val calendar = Calendar.getInstance().apply {
            time = startDay.toDate()
            add(Calendar.DAY_OF_MONTH, DataController.tourVM.getTourFromID(tourId)!!.durations - 1)
        }
        return calendar.time
    }
}
