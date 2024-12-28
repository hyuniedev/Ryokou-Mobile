package com.example.ryokoumobile.model.entity

import android.util.Log
import com.google.firebase.Timestamp
import java.text.DecimalFormat
import java.time.LocalDateTime
import kotlin.math.cos
import kotlin.math.round

data class Tour(
    var id: String = "",
    val name: String = "",
    val city: String = "",
    val durations: Int = 0,
    val start: Timestamp = Timestamp.now(),
    val maintainTime: Int = 0,
    val cost: String = "",
    val sale: Int = 0,
    val gatheringPlace: String = "",
    val freeService: Boolean = false,
    val pointo: String = "",
    val kisoku: String = "",
    val schedule: List<Schedule> = listOf(),
    val lsFile: List<String> = listOf(),
    val lsRate: List<Rate> = listOf(),
    val company: String = "",
    val end: Timestamp = Timestamp.now(),
) {
    fun getTotalRate(): Double {
        var total = 0.0
        if (lsRate.isEmpty()) return total
        lsRate.forEach {
            total += it.star
        }
        return round((total / lsRate.size) * 10) / 10.0
    }

    private fun getPriceAfterSale(): String {
        val t = (cost.replace(".", "").toLong() - (cost.replace(".", "")
            .toLong() * sale / 100)).toString()
        return t
    }

    fun getPriceWithFormatted(): String {
        return formatNumber(getPriceAfterSale())
    }

    fun getPriceWithUnformatted(): Long {
        return getPriceAfterSale().toLong()
    }

    private fun formatNumber(number: String): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number.toLong()).replace(",", ".")
    }
}
