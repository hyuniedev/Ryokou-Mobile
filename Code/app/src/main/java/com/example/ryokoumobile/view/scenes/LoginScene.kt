package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.ryokoumobile.view.components.MyTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScene() {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { MyTopBar() }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Cyan)) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScenePreview() {
    LoginScene()
}