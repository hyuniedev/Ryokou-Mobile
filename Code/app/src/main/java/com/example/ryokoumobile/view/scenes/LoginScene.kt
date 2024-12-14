package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ryokoumobile.view.components.MyTopBar

@Composable
fun LoginScene() {
    Scaffold(
        topBar = { MyTopBar() }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScenePreview() {
    LoginScene()
}