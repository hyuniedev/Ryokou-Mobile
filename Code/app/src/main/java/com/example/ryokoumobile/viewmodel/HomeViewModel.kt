package com.example.ryokoumobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.uistate.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Duration
import java.time.LocalTime

class HomeViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    fun updateTime(nextTime: LocalTime) {
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
                            LocalTime.of(_uiState.value.startTime + 2, 0)
                        )
                    }

                    else -> {
                        Duration.ZERO
                    }
                }
            )
        }
    }
}