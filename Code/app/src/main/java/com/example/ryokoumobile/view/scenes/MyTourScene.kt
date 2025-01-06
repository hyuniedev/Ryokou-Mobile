package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MyTourScene(modifier: Modifier = Modifier, navController: NavController) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("My Tour Scene", style = TextStyle(fontSize = 30.sp))
    }
}