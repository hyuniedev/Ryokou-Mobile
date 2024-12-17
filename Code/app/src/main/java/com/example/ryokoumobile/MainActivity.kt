package com.example.ryokoumobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.scenes.HomeScene
import com.example.ryokoumobile.view.scenes.LoginScene
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
//        FirebaseAppCheck.getInstance()
//            .installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance())
        enableEdgeToEdge()
        setContent {
            RyokouMobileTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Scenes.Login.route) {
        composable(Scenes.Login.route) {
            LoginScene(navController)
        }
        composable(Scenes.Home.route) {
            HomeScene()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    RyokouMobileTheme {
        Greeting()
    }
}