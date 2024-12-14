package com.example.ryokoumobile.model.entity

import java.time.LocalDateTime

data class Tour(
    val id: String = "",
    val name: String = "",
    val city: String = "",
    val durations: String = "",
    val start: LocalDateTime = LocalDateTime.now(),
    val maintainTime: Int = 0,
    val cost: String = "",
    val sale: Int = 0,
    val gatheringPlace: String = "",
    val freeService: Boolean = false,
    val pointo: String = "",
    val kisoku: String = "",
    val lsSchedule: List<Schedule> = listOf(),
    val lsFile: List<String> = listOf(),
    val lsRate: List<Rate> = listOf(),
    val company: String = "",
    val end: LocalDateTime = LocalDateTime.now()
)
