package com.example.ryokoumobile.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ryokoumobile.model.enumClass.ESex

@Composable
fun ChooseSex(sexSelected: ESex, isError: Boolean, onChange: (ESex) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Giới tính:",
            style = MaterialTheme.typography.bodyLarge,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Nam",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            MyRadioButton(selected = sexSelected == ESex.Male, isError) { onChange(ESex.Male) }
            Spacer(Modifier.width(15.dp))
            Text(
                "Nữ",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            MyRadioButton(selected = sexSelected == ESex.Female, isError) { onChange(ESex.Female) }
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun MyRadioButton(selected: Boolean, isError: Boolean, onClick: () -> Unit) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors().copy(
            selectedColor = MaterialTheme.colorScheme.tertiary,
            unselectedColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
    )
}
