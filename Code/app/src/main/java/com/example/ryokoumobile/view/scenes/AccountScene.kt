package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.view.components.MyElevatedButton

@Composable
fun AccountScene(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier.fillMaxSize()) {
        Text("Account Scene", style = TextStyle(fontSize = 30.sp))
        MyElevatedButton(title = "Log out") {
            FirebaseController.SignOut()
        }
    }
}