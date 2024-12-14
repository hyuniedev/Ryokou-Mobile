package com.example.ryokoumobile.model.entity

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val numberPhone: String,
    val password: String,
    val sex: ESex,
    var lsFavoriteTour: List<String> = listOf(),
    var lsMyTour: List<TourBooked> = listOf()
)
