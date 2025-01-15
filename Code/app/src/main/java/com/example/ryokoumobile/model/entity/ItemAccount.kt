package com.example.ryokoumobile.model.entity

import android.graphics.drawable.Icon
import androidx.compose.runtime.Composable

data class ItemAccount(
    val icon: @Composable () -> Unit = {},
    val title: String,
    val descriptor: String? = null,
    val onClick: () -> Unit = {}
)