package com.example.ryokoumobile.view.scenes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyLineTextHaveTextButton
import com.example.ryokoumobile.view.components.MyProcessWait
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.viewmodel.LoginViewModel

@Composable
fun LoginScene(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val currentContext = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value.isLoginSuccessful) {
        if (uiState.value.isLoginSuccessful) {
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.logologin),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .weight(0.8f)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f)
            ) {
                MyInputTextField(
                    stringResource(R.string.txtEmail),
                    uiState.value.email,
                    uiState.value.emailError
                ) { newValue -> viewModel.updateEmail(newValue) }
                Spacer(modifier = Modifier.height(10.dp))
                MyInputTextField(
                    stringResource(R.string.txtPassword),
                    uiState.value.password,
                    uiState.value.passwordError,
                    keyboardType = KeyboardType.Password
                ) { newValue -> viewModel.updatePassword(newValue) }
                Spacer(modifier = Modifier.height(10.dp))
                MyLineTextHaveTextButton(

                    text1 = "",
                    textButton = "Quên mật khẩu", text2 = "",
                    position = Arrangement.End
                ) { viewModel.forgetPassword() }
                Spacer(modifier = Modifier.height(20.dp))
                MyElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    "Đăng nhập"
                ) { viewModel.login(context = currentContext) }
                Spacer(modifier = Modifier.height(15.dp))
                MyElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    "   " + stringResource(R.string.loginWithGG),
                    isFilled = false,
                    painter = painterResource(R.drawable.logogoogle)
                ) { viewModel.loginWithGG(currentContext) }
                Spacer(modifier = Modifier.height(10.dp))
                MyLineTextHaveTextButton(
                    text1 = "Bạn chưa có tài khoản? ",
                    textButton = "Đăng ký.", text2 = "",
                    position = Arrangement.Center
                ) { navController.navigate(Scenes.AuthGroup.SignIn.route) }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
    if (uiState.value.isLoading) {
        MyProcessWait()
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScenePreview() {
    LoginScene()
}