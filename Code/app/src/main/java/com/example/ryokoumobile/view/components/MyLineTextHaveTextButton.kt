package com.example.ryokoumobile.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun MyLineTextHaveTextButton(
    modifier: Modifier = Modifier,
    text1: String,
    textButton: String,
    text2: String,
    position: Arrangement.Horizontal,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = position,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text1,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = textButton,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier.clickable { onClick() }
        )
        Text(
            text = text2,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}