package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.uistate.MyTourUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class MyTourViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyTourUIState())
    val uiState = _uiState.asStateFlow()

    fun updateIndexSelected(value: Int) {
        _uiState.update { it.copy(indexSelected = if (value == it.indexSelected) -1 else value) }
    }

    init {
        DataController.loadInitDataBookedTour() // init list booked tour
    }

    fun onClick(bookedTour: TourBooked) {

    }

    fun checkGoingTour(current: Date, start: Date, end: Date): Boolean {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val cur = LocalDate.parse(dateFormatter.format(current))
        val s = LocalDate.parse(dateFormatter.format(start))
        val e = LocalDate.parse(dateFormatter.format(end))
        if (cur.isEqual(s) || cur.isEqual(e)) return true
        if (cur.isAfter(s) && cur.isBefore(e)) {
            return true
        }
        return false
    }
}