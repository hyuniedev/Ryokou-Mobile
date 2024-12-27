package com.example.ryokoumobile.model.entity

import java.text.DecimalFormat
import java.time.LocalDateTime
import kotlin.math.cos

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
) {
    fun getTotalRate(): Double {
        var total = 0.0
        if (lsRate.isEmpty()) return total
        lsRate.forEach {
            total += it.star
        }
        return total / lsRate.size
    }

    private fun getPriceAfterSale(): String {
        return (cost.replace(".", "").toInt() - (cost.replace(".", "")
            .toInt() * sale / 100)).toString()
    }

    fun getPriceWithFormatted(): String {
        return formatNumber(getPriceAfterSale())
    }

    fun getPriceWithUnformatted(): Int {
        return getPriceAfterSale().toInt()
    }

    private fun formatNumber(number: String): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number.toLong()).replace(",", ".")
    }
}
