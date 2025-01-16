package com.example.ryokoumobile.model.entity

import com.example.ryokoumobile.model.enumClass.ESex
import com.google.firebase.firestore.PropertyName

data class User(
    var id: String? = null,
    val email: String = "",
    val fullName: String = "",
    @PropertyName("numberphone") val numberPhone: String = "",
    val password: String = "",
    val sex: ESex = ESex.None,
    @PropertyName("favoriteTour") var lsFavoriteTour: List<String> = listOf(),
    var lsMyTour: List<String> = listOf()
)
