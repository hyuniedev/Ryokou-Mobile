package com.example.ryokoumobile.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime

@Composable
fun FlashSaleTimeCountDown() {
    var time = remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            time.value = LocalTime.now()

        }
    }
    Row (verticalAlignment = Alignment.CenterVertically){
        BoxTime(time.value.hour.toString())
        HaiCham()
        BoxTime(time.value.minute.toString())
        HaiCham()
        BoxTime(time.value.second.toString())
    }
}

@Composable
fun HaiCham() {
    Text(
        ":",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    )
}

@Composable
fun BoxTime(time: String) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(if(time.length!=2) "0$time" else time, color = MaterialTheme.colorScheme.tertiary, style = TextStyle(fontSize = 30.sp))
    }
}