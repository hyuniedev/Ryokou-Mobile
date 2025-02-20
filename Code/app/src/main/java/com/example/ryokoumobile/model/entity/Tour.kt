package com.example.ryokoumobile.model.entity

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import java.text.DecimalFormat
import kotlin.math.round

data class Tour(
    var id: String = "",
    val name: String = "",
    val city: List<String> = listOf(),
    val durations: Int = 0,
    val start: Timestamp = Timestamp.now(),
    val maintainTime: Int = 0,
    var cost: String = "",
    val sale: Int = 0,
    val gatheringPlace: String = "",
    val freeService: Boolean = false,
    val pointo: String = "",
    val kisoku: String = "",
    val schedule: List<Schedule> = listOf(),
    val lsFile: List<String> = listOf(),
    @get:Exclude var lsRate: List<Rate> = listOf(),
    val company: String = "",
    var ticketLimit: TicketLimit = TicketLimit()
) {
    @Exclude
    fun getTotalRate(): Double {
        var total = 0.0
        if (lsRate.isEmpty()) return total
        lsRate.forEach {
            total += it.star
        }
        return round((total / lsRate.size) * 10) / 10.0
    }

    @Exclude
    private fun getPriceAfterSale(): String {
        val t = (cost.replace(".", "").toLong() - (cost.replace(".", "")
            .toLong() * sale / 100)).toString()
        return t
    }

    @Exclude
    fun getPriceWithFormatted(): String {
        return formatNumber(getPriceAfterSale())
    }

    @Exclude
    fun getPriceWithUnformatted(): Long {
        return getPriceAfterSale().toLong()
    }

    @Exclude
    private fun formatNumber(number: String): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number.toLong()).replace(",", ".")
    }
}
