package com.example.ryokoumobile.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

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
