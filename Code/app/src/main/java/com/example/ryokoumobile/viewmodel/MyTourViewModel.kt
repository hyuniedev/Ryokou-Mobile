package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.Schedule
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.uistate.MyTourUIState
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.time.LocalDate

class MyTourViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyTourUIState())
    val uiState = _uiState.asStateFlow()

    init {
        if (DataController.user.value != null) funListTour()
    }

    fun updateIndexSelected(value: Int) {
        _uiState.update { it.copy(indexSelected = if (value == it.indexSelected) -1 else value) }
    }

    fun onClick(bookedTour: TourBooked) {
        _uiState.update { it.copy(bookedTourFocus = bookedTour) }
    }

    fun unfocusBookedTour() {
        _uiState.update { it.copy(bookedTourFocus = null, selectedDayOnSchedule = Schedule()) }
    }

    fun updateSelectedDayOnSchedule(schedule: Schedule) {
        _uiState.update { it.copy(selectedDayOnSchedule = schedule) }
    }

    private fun funListTour() {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val now = Timestamp.now()
        val current = LocalDate.parse(dateFormatter.format(now.toDate()))
        DataController.lsBookedTour.forEach { bookedTour ->
            val s = LocalDate.parse(dateFormatter.format(bookedTour.startDay.toDate()))
            val e = LocalDate.parse(dateFormatter.format(bookedTour.getEndDay()))
            if (current.isAfter(e)) {
                _uiState.update { it.copy(lsGoneTour = it.lsGoneTour + bookedTour) }
            } else if (current.isBefore(s)) {
                _uiState.update { it.copy(lsWaitTour = it.lsWaitTour + bookedTour) }
            } else {
                _uiState.update { it.copy(lsGoingTour = it.lsGoingTour + bookedTour) }
            }
        }
    }
}