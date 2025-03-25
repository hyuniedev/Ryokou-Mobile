package com.example.ryokoumobile.model.controller

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.enumClass.EProvince

object UserAnalytics {
    var lsSimilarTour: List<Tour> = mutableStateListOf()
    var lsDealTour: List<Tour> = mutableStateListOf()
    var lsMostPopularTour: List<Tour> = mutableStateListOf()
    var lsMostBookedTour: List<Tour> = mutableStateListOf()
    var mapTopProvince: MutableMap<String, Int> = mutableStateMapOf()

    private var fromHour = -1
    fun updateSimilarTours() {
        lsSimilarTour = DataController.tourVM.uiState.value
        val list = mutableSetOf<Tour>()
        if (DataController.user.value != null) {
            for (bookedTour in DataController.lsBookedTour) {
                val curTour = DataController.tourVM.getTourFromID(bookedTour.tourId)
                for (city in curTour?.city ?: listOf()) {
                    list.addAll(DataController.tourVM.uiState.value.filter {
                        it.city.contains(city)
                    })
                }
            }
            for (favoTour in DataController.user.value!!.lsFavoriteTour) {
                val tour = DataController.tourVM.getTourFromID(favoTour)
                for (city in tour?.city ?: listOf()) {
                    list.addAll(DataController.tourVM.uiState.value.filter {
                        it.city.contains(city)
                    })
                }
            }
        }
        if (list.isNotEmpty() && lsSimilarTour != list.toList()) {
            lsSimilarTour =
                list.toList().shuffled().subList(0, if (list.size < 10) list.size else 10)
        }
    }

    fun updateMostPopularTour() {
        val lsDataTour = DataController.tourVM.uiState.value
        lsDataTour.forEach { tour ->
            for (i in tour.city) {
                mapTopProvince[i] = mapTopProvince.getOrPut(i) { 0 } + 1
            }
        }
        updateMostBookedTour(lsDataTour)
        lsMostPopularTour = lsDataTour.sortedByDescending { it.getTotalRate() }
            .subList(0, if (lsDataTour.size < 10) lsDataTour.size else 10)
    }

    private fun updateMostBookedTour(lsDataTour: List<Tour>) {
        val result = lsDataTour.sortedByDescending { tour -> tour.ticketLimit.numCurrentTicket }
        lsMostBookedTour = result.subList(0, if (result.size < 10) result.size else 10)
    }


    fun updateDealTours(from: Int) {
        if (from != fromHour || lsDealTour.isEmpty()) {
            fromHour = from
            lsDealTour = DataController.tourVM.uiState.value.shuffled()
        }
    }

    fun getTopProvince(): List<EProvince> {
        val map = mapTopProvince.toList().sortedByDescending { it.second }
            .map { EProvince.valueOf(it.first.uppercase()) }
        return map.subList(0, if (map.size < 5) map.size else 5)
    }

    fun getSimilarTour(tour: Tour): List<Tour> {
        val lsTour = DataController.tourVM.uiState.value
        val ls = mutableSetOf<Tour>()
        lsTour.forEach { checkTour ->
            checkTour.city.forEach { city ->
                if (tour.city.contains(city)) ls.add(checkTour)
            }
            if (checkTour.company == tour.company) ls.add(checkTour)
        }
        return ls.toList().shuffled().subList(0, if (ls.size < 10) ls.size else 10)
    }
}