package com.example.ryokoumobile.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ShowInfoDialog(showDialog: Boolean, text: String, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Thông báo") },
            text = { Text(text) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }

        )
    }
}


@Composable
fun ShowConfirmDialog(
    showDialog: Boolean,
    text: String,
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Xác nhận") },
            text = { Text(text) },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    onAccept()
                }) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
fun ShowReportDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text = remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Nhập nội dung") },
            text = {
                Column {
                    Text("Vui lòng nhập:")
                    TextField(
                        value = text.value,
                        onValueChange = { text.value = it },
                        placeholder = { Text("Nhập tại đây...") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onDismiss()
                    onConfirm(text.value)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Hủy")
                }
            }
        )
    }
}
