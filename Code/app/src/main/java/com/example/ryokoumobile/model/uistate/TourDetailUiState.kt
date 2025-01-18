package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.Schedule
import com.google.firebase.Timestamp

data class TourDetailUiState(
    var companyName: String = "",
    var isShowSchedule: Boolean = false,
    var selectedDayOnSchedule: Schedule = Schedule(),
    var isExtend: Boolean = false,
    var lsRate: List<Rate> = listOf(),
    var numRateOfUser: Int = 0,
    var userComment: String = "",
    var numShowRate: Int = 2,
    var isSelectTour: Boolean = false,
    var isShowDatePicker: Boolean = false,
    var dateSelected: Timestamp? = null,
    var numTicket: Int = 0
)