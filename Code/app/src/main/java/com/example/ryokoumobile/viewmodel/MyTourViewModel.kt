package com.example.ryokoumobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.uistate.MyTourUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
}