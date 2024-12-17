package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.MyInputTextField
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
        if (uiState.value.isLoginSuccessful){
            navController.navigate(Scenes.Home.route)
        }
    }

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
                MyLineStringHaveTextButton(
                    "",
                    "Quên mật khẩu",
                    Arrangement.End
                ) { viewModel.forgetPassword() }
                Spacer(modifier = Modifier.height(20.dp))
                MyElevatedButton("Login") { viewModel.login() }
                Spacer(modifier = Modifier.height(10.dp))
                MyElevatedButton(
                    "  " + stringResource(R.string.loginWithGG),
                    painter = painterResource(R.drawable.logogoogle)
                ) { viewModel.loginWithGG(currentContext) }
                Spacer(modifier = Modifier.height(10.dp))
                MyLineStringHaveTextButton(
                    "Don't have an account? ",
                    "Sign in.",
                    Arrangement.Center
                ) { viewModel.signIn() }
            }
            if (uiState.value.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun MyElevatedButton(title: String, painter: Painter? = null, onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors().copy(
            containerColor = if (painter != null) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
            contentColor = if (painter != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        if (painter != null) {
            Image(painter = painterResource(R.drawable.logogoogle), contentDescription = null)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 3.dp)
        )
    }
}

@Composable
fun MyLineStringHaveTextButton(
    text1: String,
    text2: String,
    position: Arrangement.Horizontal,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = position,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text1, style = MaterialTheme.typography.labelSmall)
        Text(
            text = text2,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScenePreview() {
    LoginScene()
}