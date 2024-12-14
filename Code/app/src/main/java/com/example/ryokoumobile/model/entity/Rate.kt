package com.example.ryokoumobile.model.entity

data class Rate(
    val id: String,
    val userId: String,
    val tourId: String,
    val star: Int,
    val comment: String
)
