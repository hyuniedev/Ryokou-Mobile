package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ryokoumobile.R
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyTopBar

@Composable
fun LoginScene() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { MyTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logologin),
                contentDescription = "",
                modifier = Modifier.size(300.dp).weight(1f)
            )
            Column(modifier = Modifier.padding(horizontal = 20.dp).weight(1f)) {
                MyInputTextField("Email:", email) { newValue -> email = newValue }
                Spacer(modifier = Modifier.height(10.dp))
                MyInputTextField("Password:", password) { newValue -> password = newValue }
                Spacer(modifier = Modifier.height(10.dp))
                MyLineStringHaveTextButton("", "Quên mật khẩu", Arrangement.End) { }
                Spacer(modifier = Modifier.height(20.dp))
                MyElevatedButton("Login") { }
                Spacer(modifier = Modifier.height(10.dp))
                MyLineStringHaveTextButton(
                    "Nếu không có tài khoản?. ",
                    "Đăng ký.",
                    Arrangement.Center
                ) { }
            }
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.weight(0.5f))
            }
        }
    }
}

@Composable
fun MyElevatedButton(title: String, onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
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