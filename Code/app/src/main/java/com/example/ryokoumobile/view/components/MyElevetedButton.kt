package com.example.ryokoumobile.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.unit.dp

@Composable
fun MyElevatedButton(
    modifier: Modifier = Modifier,
    title: String,
    isFilled: Boolean = true,
    painter: Painter? = null,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors().copy(
            containerColor = if (!isFilled) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
            contentColor = if (!isFilled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        if (painter != null) {
            Image(painter = painter, contentDescription = null)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 3.dp)
        )
    }
}