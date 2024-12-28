package com.example.ryokoumobile.view.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ryokoumobile.model.enumClass.EMonth
import com.example.ryokoumobile.model.enumClass.EProvince

@Composable
fun <T> ItemCapsule(title: T, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = when (title) {
                is EProvince -> title.nameProvince
                is EMonth -> title.monthName
                else -> ""
            },
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
            )
        )
    }
}