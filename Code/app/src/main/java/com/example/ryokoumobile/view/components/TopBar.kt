package com.example.ryokoumobile.view.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ryokoumobile.R

@Composable
fun MyTopBar() {
    Text(
        text = stringResource(R.string.app_name),
        style = TextStyle(fontSize = 34.sp, color = Color.White, fontWeight = FontWeight.Bold)
    )
}