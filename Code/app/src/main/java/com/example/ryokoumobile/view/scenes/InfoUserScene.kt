package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.viewmodel.InfoUserViewModel

@Composable
fun InfoUserScene(infoUserVM: InfoUserViewModel = viewModel()) {
    val uiState = infoUserVM.uiState.collectAsState()
    Scaffold(topBar = { MyTopBar() }) { paddingContent ->
        Column(
            modifier = Modifier
                .padding(paddingContent)
                .padding(horizontal = 15.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(27.dp)
                    )
                    IconButton(onClick = { infoUserVM.updateIsEditing() }) {
                        Icon(
                            if (uiState.value.isEditing) Icons.Default.Done else Icons.Default.Create,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(27.dp)
                        )
                    }
                }
                if (uiState.value.isEditing) {

                } else {
                    val user = DataController.user.collectAsState()
                    RowShowInfo("Họ và tên", user.value!!.fullName)
                    RowShowInfo("Số điện thoại", user.value!!.numberPhone)
                    RowShowInfo("Email", user.value!!.email)
                    RowShowInfo("Giới tính", user.value!!.sex.name)
                }
            }
        }
    }
}

@Composable
private fun RowShowInfo(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            title,
            style = TextStyle(
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )
        Text(value, style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InfoUserScenePreview() {
    RyokouMobileTheme {
        InfoUserScene()
    }
}
