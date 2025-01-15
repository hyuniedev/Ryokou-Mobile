package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.enumClass.ESex
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyLineTextHaveTextButton
import com.example.ryokoumobile.view.components.MyProcessWait
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.viewmodel.SignInViewModel

@Composable
fun SignInScene(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: SignInViewModel = viewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value.isSignInSuccess) {
        if (uiState.value.isSignInSuccess) {
            navController.navigate(Scenes.MainGroup.Home.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.imgwelcome),
                contentDescription = null,
                modifier = Modifier
                    .scale(2.5f)
                    .padding(vertical = 40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            SignInTextField(
                "Họ và Tên",
                uiState.value.name,
                uiState.value.nameError
            ) { newValue -> viewModel.updateName(newValue) }
            SignInTextField(
                "Email",
                uiState.value.email,
                uiState.value.emailError
            ) { newValue -> viewModel.updateEmail(newValue) }
            ChooseSex(
                uiState.value.sex,
                uiState.value.sexError
            ) { newValue -> viewModel.updateSex(newValue) }
            SignInTextField(
                "Số điện thoại",
                uiState.value.numberPhone,
                uiState.value.numberPhoneError
            ) { newValue -> viewModel.updateNumberPhone(newValue) }
            SignInTextField(
                "Mật khẩu",
                uiState.value.password,
                uiState.value.passwordError
            ) { newValue -> viewModel.updatePassword(newValue) }
            SignInTextField(
                "Xác thực mật khẩu",
                uiState.value.passwordConfirm,
                uiState.value.passwordConfirmError
            ) { newValue -> viewModel.updatePasswordConfirm(newValue) }
            Spacer(Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.value.cbService,
                    colors = CheckboxDefaults.colors()
                        .copy(uncheckedBorderColor = MaterialTheme.colorScheme.primary),
                    onCheckedChange = { viewModel.updateCheckBoxService() })
                MyLineTextHaveTextButton(
                    "Tôi đồng ý với ",
                    "Điều khoản & chính sách bảo mật",
                    ".",
                    Arrangement.Start
                ) {
                    //TODO: Hiển thị ShowBottomSheet cho điều khoản & dịch vụ
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            MyElevatedButton(modifier = Modifier.fillMaxWidth(), "Đăng ký") {
                viewModel.signIn(context)
            }
            Spacer(modifier = Modifier.height(15.dp))
            MyElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.logogoogle),
                isFilled = false,
                title = "   " + stringResource(R.string.signInWithGG)
            ) { viewModel.signInWithGG(context) }
            Spacer(Modifier.height(20.dp))
        }
    }
    if (uiState.value.isLoading) {
        MyProcessWait()
    }

}

@Composable
private fun ChooseSex(sexSelected: ESex, isError: Boolean, onChange: (ESex) -> Unit) {
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

@Composable
private fun SignInTextField(
    title: String,
    value: String,
    isError: Boolean,
    onChange: (String) -> Unit
) {
    MyInputTextField(title, value, isError, onValueChange = onChange)
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInScenePreview() {
    RyokouMobileTheme {
        SignInScene()
    }
}