package com.example.ryokoumobile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ryokoumobile.R

@Composable
fun RangeTimeScale(startTime: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(15.dp)
            )
            .fillMaxWidth()
            .fillMaxHeight(0.14f)
            .clip(RoundedCornerShape(15.dp))
            .background(color = Color(0xFFFEE7ED)),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.dealHot),
                style = TextStyle(color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp),
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "${startTime}H - ${startTime + 2}H",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
            )
            Text(
                stringResource(R.string.vuicungRyokou),
                style = TextStyle(color = MaterialTheme.colorScheme.tertiary, fontSize = 14.sp)
            )
        }
    }
}