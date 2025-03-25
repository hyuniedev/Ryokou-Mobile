package com.example.ryokoumobile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ryokoumobile.model.entity.Schedule
import com.example.ryokoumobile.model.entity.ToDoOnDay
import com.example.ryokoumobile.model.entity.Tour

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetShowTourSchedule(
    onDismissRequest: () -> Unit,
    tour: Tour,
    selectedDayOnSchedule: Schedule,
    updateSelectedDayOnSchedule: (schedule: Schedule) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SubModalBottomSheetShowTourSchedule(
            tour = tour,
            selectedDayOnSchedule = selectedDayOnSchedule,
            updateSelectedDayOnSchedule = updateSelectedDayOnSchedule
        )
    }
}

@Composable
fun SubModalBottomSheetShowTourSchedule(
    tour: Tour,
    selectedDayOnSchedule: Schedule,
    showIcon: Boolean = true,
    updateSelectedDayOnSchedule: (schedule: Schedule) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
    ) {
        if (showIcon) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn {
                items(tour.schedule) { schedule ->
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
                            .background(if (selectedDayOnSchedule == schedule) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                            .clickable { updateSelectedDayOnSchedule(schedule) }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            schedule.day,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    .padding(5.dp)
            ) {
                LazyColumn {
                    items(selectedDayOnSchedule.lsTodo) { toDo ->
                        LineToDo(toDo, tour)
                    }
                }
            }
        }
    }
}

@Composable
private fun LineToDo(toDo: ToDoOnDay, tour: Tour) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
            "${toDo.hour}:${toDo.minute}",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
        )
        Spacer(Modifier.width(5.dp))
        Column {
            Text(
                toDo.content,
                maxLines = Int.MAX_VALUE,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
            Row {
                Icon(
                    Icons.Default.Place,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Địa điểm: ${toDo.location.ifEmpty { tour.city[0] }}")
            }
        }
    }
}