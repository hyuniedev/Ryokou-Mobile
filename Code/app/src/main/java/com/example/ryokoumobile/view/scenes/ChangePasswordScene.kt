package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.components.ShowConfirmDialog
import com.example.ryokoumobile.view.components.ShowInfoDialog
import com.example.ryokoumobile.viewmodel.ChangePasswordViewModel

@Composable
fun ChangePasswordScene(
    navController: NavController,
    changePasswordVM: ChangePasswordViewModel = viewModel()
) {
    val focus = LocalFocusManager.current
    val uiState = changePasswordVM.uiState.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = { MyTopBar() },
        modifier = Modifier.clickable(
            interactionSource = null,
            indication = null
        ) { focus.clearFocus() }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.lock_change_password),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 20.dp)
                )
                MyInputTextField(
                    "Mật khẩu hiện tại",
                    onValueChange = { value -> changePasswordVM.updateCurrentPassword(value) },
                    value = uiState.value.currentPassword,
                    isError = uiState.value.isCurrentPasswordError,
                    keyboardType = KeyboardType.Password
                )
                MyInputTextField(
                    "Mật khẩu mới",
                    onValueChange = { value -> changePasswordVM.updateNewPassword(value) },
                    value = uiState.value.newPassword,
                    isError = uiState.value.isNewPasswordError,
                    keyboardType = KeyboardType.Password
                )
                MyInputTextField(
                    "Xác thực mật khẩu mới",
                    onValueChange = { value -> changePasswordVM.updateConfirmNewPassword(value) },
                    value = uiState.value.confirmNewPassword,
                    isError = uiState.value.isConfirmPasswordError,
                    keyboardType = KeyboardType.Password
                )
                MyElevatedButton(title = "Xác nhận") {
                    changePasswordVM.onClickConfirmButton(context)
                }
                Spacer(Modifier.height(50.dp))
            }
        }
    }
    ShowInfoDialog(
        showDialog = uiState.value.isShowNotPasswordAccountDialog,
        text = "Tài khoản này không thể đổi mật khẩu."
    ) {
        changePasswordVM.offShowNotPasswordAccountDialog()
    }
    ShowConfirmDialog(
        showDialog = uiState.value.isShowConfirmDialog,
        text = "Bạn có xác nhận đổi mật khẩu?",
        onDismiss = { changePasswordVM.offConfirmDialog() },
        onAccept = {
            changePasswordVM.onClickConfirmDialog()
        })
    ShowInfoDialog(
        showDialog = uiState.value.isCompleted,
        "Thay đổi mật khẩu thành công."
    ) {
        changePasswordVM.offCompletedDialog()
        navController.popBackStack()
    }
}