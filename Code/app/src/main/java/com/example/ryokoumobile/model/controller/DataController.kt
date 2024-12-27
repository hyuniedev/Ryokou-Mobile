package com.example.ryokoumobile.model.controller

import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.repository.lsPager

object DataController {
    var user: User? = null
    var lsTour: List<Tour> =
        listOf(
            Tour(
                name = "Ngắm hoa anh đào tại Tokyo",
                cost = "12.000.000",
                sale = 25,
                lsFile = lsPager
            )
        )
}