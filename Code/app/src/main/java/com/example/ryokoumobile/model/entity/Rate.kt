package com.example.ryokoumobile.model.entity

data class Rate(
    var id: String = "",
    val userId: String = "",
    val tourId: String = "",
    val star: Int = 0,
    val comment: String = ""
)
