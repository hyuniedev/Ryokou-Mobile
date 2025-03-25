package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.entity.Schedule
import com.example.ryokoumobile.model.entity.TourBooked

data class MyTourUIState(
    var indexSelected: Int = 0,
    var lsGoingTour: List<TourBooked> = listOf(),
    var lsGoneTour: List<TourBooked> = listOf(),
    var lsWaitTour: List<TourBooked> = listOf(),
    var bookedTourFocus: TourBooked? = null,
    var selectedDayOnSchedule: Schedule = Schedule(),
    var isScheduleShow: Boolean = true,
    var isShowCancelTourDialog: Boolean = false,
    var isShowReportTourDialog: Boolean = false,
    var isShowSupportRequestDialog: Boolean = false
)
