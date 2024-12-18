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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.entity.ESex
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyProcessWait
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.viewmodel.SignInViewModel

@Composable
fun SignInScene(viewModel: SignInViewModel = viewModel()) {
    val forcusManager = LocalFocusManager.current
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { MyTopBar() },
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) { forcusManager.clearFocus() }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
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
                    "Full name",
                    uiState.value.name,
                    uiState.value.nameError
                ) { newValue -> viewModel.updateName(newValue) }
                SignInTextField(
                    "Email",
                    uiState.value.email,
                    uiState.value.emailError
                ) { newValue -> viewModel.updateEmail(newValue) }
                ChooseSex(uiState.value.sex) { newValue -> viewModel.updateSex(newValue) }
                SignInTextField(
                    "Number phone",
                    uiState.value.numberPhone,
                    uiState.value.numberPhoneError
                ) { newValue -> viewModel.updateNumberPhone(newValue) }
                SignInTextField(
                    "Password",
                    uiState.value.password,
                    uiState.value.passwordError
                ) { newValue -> viewModel.updatePassword(newValue) }
                SignInTextField(
                    "Confirm password",
                    uiState.value.passwordConfirm,
                    uiState.value.passwordConfirmError
                ) { newValue -> viewModel.updatePasswordConfirm(newValue) }
                Spacer(modifier = Modifier.height(20.dp))
                MyElevatedButton("Sign in") { viewModel.signIn() }
                Spacer(modifier = Modifier.height(15.dp))
                MyElevatedButton(
                    painter = painterResource(R.drawable.logogoogle),
                    title = "   " + stringResource(R.string.signInWithGG)
                ) { viewModel.signInWithGG() }
                Spacer(Modifier.height(20.dp))
            }
        }
        if (uiState.value.isLoading) {
            MyProcessWait()
        }
    }
}

@Composable
private fun ChooseSex(sexSelected: ESex, onChange: (ESex) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Your sex:",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Male",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            MyRadioButton(selected = sexSelected == ESex.Male) { onChange(ESex.Male) }
            Spacer(Modifier.width(15.dp))
            Text(
                "Female",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            MyRadioButton(selected = sexSelected == ESex.Female) { onChange(ESex.Female) }
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun MyRadioButton(selected: Boolean, onClick: () -> Unit) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors().copy(
            selectedColor = MaterialTheme.colorScheme.tertiary,
            unselectedColor = MaterialTheme.colorScheme.primary
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
    MyInputTextField(title, value, isError, onChange)
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInScenePreview() {
    RyokouMobileTheme {
        SignInScene()
    }
}