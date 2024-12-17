package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyTopBar

@Composable
fun HomeScene() {
    Scaffold(topBar = { MyTopBar() }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Home Scene", style = TextStyle(fontSize = 51.sp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScenePreview() {
    RyokouMobileTheme {
        HomeScene()
    }
}