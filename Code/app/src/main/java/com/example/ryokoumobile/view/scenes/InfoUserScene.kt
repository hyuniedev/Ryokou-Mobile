package com.example.ryokoumobile.view.scenes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.ChooseSex
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.items.ItemSetting
import com.example.ryokoumobile.viewmodel.InfoUserViewModel

@Composable
fun InfoUserScene(infoUserVM: InfoUserViewModel = viewModel(), navController: NavController) {
    val uiState = infoUserVM.uiState.collectAsState()
    val focus = LocalFocusManager.current
    val context = LocalContext.current
    Scaffold(
        topBar = { MyTopBar() },
        modifier = Modifier.clickable(
            interactionSource = null,
            indication = null
        ) { focus.clearFocus() }) { paddingContent ->
        Column(
            modifier = Modifier
                .padding(paddingContent)
                .padding(horizontal = 15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 10.dp, top = 5.dp)
                    .animateContentSize()
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
                    IconButton(onClick = { infoUserVM.updateIsEditing(context) }) {
                        Icon(
                            if (uiState.value.isEditing) Icons.Default.Done else Icons.Default.Create,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(27.dp)
                        )
                    }
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier.padding(top = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    if (uiState.value.isEditing) {
                        MyInputTextField(
                            title = "Họ và tên",
                            value = uiState.value.fullname,
                            isError = uiState.value.isFullNameError
                        ) { value -> infoUserVM.updateFullName(value) }
                        MyInputTextField(
                            title = "Số điện thoại",
                            value = uiState.value.numberPhone,
                            isError = uiState.value.isNumberPhoneError
                        ) { value -> infoUserVM.updateNumberPhone(value) }
                        MyInputTextField(
                            title = "Email",
                            value = uiState.value.email,
                            isError = uiState.value.isEmailError
                        ) { value -> infoUserVM.updateEmail(value) }
                        ChooseSex(
                            sexSelected = uiState.value.sex,
                            isError = uiState.value.isSexError
                        ) { value -> infoUserVM.updateSex(value) }
                    } else {
                        val user = DataController.user.collectAsState()
                        RowShowInfo("Họ và tên", user.value?.fullName ?: "")
                        RowShowInfo("Số điện thoại", user.value?.numberPhone ?: "")
                        RowShowInfo("Email", user.value?.email ?: "")
                        RowShowInfo("Giới tính", user.value?.sex?.name ?: "None")
                    }
                }
            }
            Spacer(Modifier.height(40.dp))
            Box(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15.dp)
                )
            ) {
                ItemSetting(
                    title = "Đổi mật khẩu",
                    descriptor = "Nhấn để thay đổi mật khẩu",
                    onClick = {})
            }
            Spacer(Modifier.height(20.dp))
            MyElevatedButton(title = "Đăng xuất") { infoUserVM.logout(navController) }
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

