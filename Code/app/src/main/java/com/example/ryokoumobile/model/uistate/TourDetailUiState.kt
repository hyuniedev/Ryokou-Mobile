package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.Schedule

data class TourDetailUiState(
    var companyName: String = "",
    var isShowSchedule: Boolean = false,
    var selectedDayOnSchedule: Schedule = Schedule(),
    var isExtend: Boolean = false,
    var lsRate: List<Rate> = listOf()
)