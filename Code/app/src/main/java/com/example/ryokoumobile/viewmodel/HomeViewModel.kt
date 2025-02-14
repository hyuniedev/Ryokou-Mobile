package com.example.ryokoumobile.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.enumClass.EMonth
import com.example.ryokoumobile.model.enumClass.EProvince
import com.example.ryokoumobile.model.uistate.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HomeViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    fun updateTime(nextTime: LocalDateTime) {
        _uiState.update {
            it.copy(
                currentTime = nextTime,
                startTime =
                if (uiState.value.currentTime.hour % 2 == 0)
                    uiState.value.currentTime.hour
                else
                    uiState.value.currentTime.hour - 1
            )
        }
    }

    fun updateDuration() {
        _uiState.update {
            it.copy(
                duration = when {
                    _uiState.value.currentTime.hour < _uiState.value.startTime + 2 -> {
                        Duration.between(
                            _uiState.value.currentTime,
                            if (_uiState.value.startTime + 2 < 23)
                                LocalDateTime.of(
                                    LocalDate.now(),
                                    LocalTime.of(_uiState.value.startTime + 2, 0)
                                )
                            else
                                LocalDateTime.of(
                                    LocalDate.now().plusDays(1),
                                    LocalTime.of((_uiState.value.startTime + 2) % 24, 0)
                                )
                        )
                    }

                    else -> {
                        Duration.ZERO
                    }
                }
            )
        }
    }

    fun updateItemSelected1(eMonth: EMonth) {
        _uiState.update { it.copy(itemSelected1 = eMonth) }
    }

    fun updateItemSelected2(eProvince: EProvince) {
        _uiState.update { it.copy(itemSelected2 = eProvince) }
    }
}