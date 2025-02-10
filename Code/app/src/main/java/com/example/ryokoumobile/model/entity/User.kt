package com.example.ryokoumobile.model.entity

import com.example.ryokoumobile.model.enumClass.ESex

data class User(
    var id: String? = null,
    val email: String = "",
    val fullName: String = "",
    val numberPhone: String = "",
    val password: String = "",
    val sex: ESex = ESex.None,
    var lsFavoriteTour: List<String> = listOf()
)
