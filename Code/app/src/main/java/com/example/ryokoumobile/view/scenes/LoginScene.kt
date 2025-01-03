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
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val currentContext = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value.isLoginSuccessful) {
        if (uiState.value.isLoginSuccessful) {
            navController.navigate(Scenes.Home.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { MyTopBar() },
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
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
                        uiState.value.passwordError
                    ) { newValue -> viewModel.updatePassword(newValue) }
                    Spacer(modifier = Modifier.height(10.dp))
                    MyLineTextHaveTextButton(
                        "",
                        "Quên mật khẩu", "",
                        Arrangement.End
                    ) { viewModel.forgetPassword() }
                    Spacer(modifier = Modifier.height(20.dp))
                    MyElevatedButton("Login") { viewModel.login() }
                    Spacer(modifier = Modifier.height(15.dp))
                    MyElevatedButton(
                        "   " + stringResource(R.string.loginWithGG),
                        painter = painterResource(R.drawable.logogoogle)
                    ) { viewModel.loginWithGG(currentContext) }
                    Spacer(modifier = Modifier.height(10.dp))
                    MyLineTextHaveTextButton(
                        "Don't have an account? ",
                        "Sign in.", "",
                        Arrangement.Center
                    ) { navController.navigate(Scenes.SignIn.route) }
                }
                Spacer(Modifier.height(20.dp))
            }
        }
        if (uiState.value.isLoading) {
            MyProcessWait()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScenePreview() {
    LoginScene()
}