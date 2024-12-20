package com.example.ryokoumobile.view.components

import android.content.Context
import android.widget.Toast

fun MyShowToast(context: Context, text: String) {
    Toast.makeText(
        context, text,
        Toast.LENGTH_SHORT
    ).show()
}