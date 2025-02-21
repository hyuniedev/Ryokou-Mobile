package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.enumClass.EMonth
import com.example.ryokoumobile.model.enumClass.EProvince
import java.time.Duration
import java.time.LocalDateTime

data class HomeUIState(
    var currentTime: LocalDateTime = LocalDateTime.now(),
    var startTime: Int = 0,
    var duration: Duration = Duration.ZERO,
    var lsSection1: List<EMonth> = EMonth.values().toList(),
    var itemSelected1: EMonth = EMonth.JANUARY,
    var lsSection2: List<EProvince> = EProvince.values().toList(),
    var itemSelected2: EProvince = EProvince.HOKKAIDO,
//    var lsSaleTour: List<Tour> = listOf(),
//    var lsTourSection1: List<Tour> = listOf(),
//    var lsTourSection2: List<Tour> = listOf()
)
