package com.example.ryokoumobile.model.controller

import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.viewmodel.TourViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

object DataController {
    var user = MutableStateFlow<User?>(null)
    fun updateFavoriteTour(tour: Tour) {
        val ls = user.value?.lsFavoriteTour?.toMutableList() ?: mutableListOf()
        if (ls.contains(tour.id)) {
            ls.remove(tour.id)
        } else {
            ls.add(tour.id)
        }
        user.update {
            it?.copy(lsFavoriteTour = ls)
        }
    }

    val tourVM = TourViewModel()
}