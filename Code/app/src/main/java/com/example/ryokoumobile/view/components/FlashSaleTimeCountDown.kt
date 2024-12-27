package com.example.ryokoumobile.view.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalTime

@Composable
fun FlashSaleTimeCountDown(duration: Duration) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        BoxTime(duration.toHours().toString())
        HaiCham()
        BoxTime((duration.toMinutes() % 60).toString())
        HaiCham()
        BoxTime((duration.seconds % 60).toString())
    }
}

@Composable
fun HaiCham() {
    Text(
        ":",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier.padding(horizontal = 3.dp)
    )
}

@Composable
fun BoxTime(time: String) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            if (time.length != 2) "0$time" else time,
            color = MaterialTheme.colorScheme.tertiary,
            style = TextStyle(fontSize = 25.sp)
        )
    }
}