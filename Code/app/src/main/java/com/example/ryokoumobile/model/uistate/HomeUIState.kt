package com.example.ryokoumobile.model.uistate

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

data class HomeUIState(
    var currentTime: LocalDateTime = LocalDateTime.now(),
    var startTime: Int = 0,
    var duration: Duration = Duration.ZERO
)
