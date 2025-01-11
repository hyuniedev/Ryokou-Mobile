package com.example.ryokoumobile.model.controller

import android.util.Log
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.viewmodel.TourViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

object DataController {
    var user = MutableStateFlow<User?>(null)
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

    val tourVM = TourViewModel()
}