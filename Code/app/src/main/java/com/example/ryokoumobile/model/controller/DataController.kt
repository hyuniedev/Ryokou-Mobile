package com.example.ryokoumobile.model.controller

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.enumClass.EVariationTicket
import com.example.ryokoumobile.viewmodel.NotificationViewModel
import com.example.ryokoumobile.viewmodel.TourViewModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

object DataController {
    var user = MutableStateFlow<User?>(null)
    val tourVM = TourViewModel()
    val notificationVM = NotificationViewModel()

    var lsBookedTour = mutableStateListOf<TourBooked>()

    fun updateFavoriteTour(tour: Tour) {
        if (user.value == null) return
        val ls = user.value?.lsFavoriteTour?.toMutableList() ?: mutableListOf()
        if (ls.contains(tour.id)) {
            ls.remove(tour.id)
        } else {
            if (tourVM.getTourFromID(tour.id) != null)
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
        tourVM.setNumCurrentTicket(
            tourVM.getTourFromID(tourBooked.tourId)!!,
            tourBooked,
            EVariationTicket.INC
        )
    }

    fun loadInitDataBookedTour() {
        if (user.value == null) return

        lsBookedTour = mutableStateListOf()

        FirebaseController.firestore.collection("bookedTours")
            .whereEqualTo("userId", user.value!!.id!!).get()
            .addOnSuccessListener { datas ->
                for (data in datas) {
                    val x = data.toObject(TourBooked::class.java)
                    val tour = tourVM.getTourFromID(x.tourId)
                    if (tour == null) {
                        notificationVM.notifyDeletedTour(x.tourId)
                        continue
                    }
                    lsBookedTour.add(x)
                }
                //----------Tạo thông báo cho tour----------------
                for (i in lsBookedTour.toList()) {
                    val tourStartDate = notificationVM.revertToLocalDate(i.startDay.toDate())
                    val tourEndDate = notificationVM.revertToLocalDate(i.getEndDay())
                    val currentDate = notificationVM.revertToLocalDate(Timestamp.now().toDate())
                    if (tourStartDate.year == currentDate.year && tourStartDate.monthValue == currentDate.monthValue && currentDate.dayOfMonth + 1 == tourStartDate.dayOfMonth) {
                        notificationVM.notifyStartTour(tourVM.getTourFromID(i.tourId)!!)
                    } else if (tourEndDate == currentDate) {
                        notificationVM.notifyEndTour(tourVM.getTourFromID(i.tourId)!!)
                    }
                }
            }.addOnFailureListener {
                Log.e("HyuNie", "Error on load Booked Tour: ${it.message}")
            }
    }
}