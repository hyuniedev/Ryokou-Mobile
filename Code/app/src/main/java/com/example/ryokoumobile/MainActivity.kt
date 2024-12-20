package com.example.ryokoumobile

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyShowToast
import com.example.ryokoumobile.view.scenes.HomeScene
import com.example.ryokoumobile.view.scenes.LoginScene
import com.example.ryokoumobile.view.scenes.SignInScene
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val backToOutApp = remember { MutableStateFlow(false) }
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if(backToOutApp.value){
            (context as? Activity)?.finish()
        }else{
            backToOutApp.value = true
            MyShowToast(context,"Press back again to exit")
            coroutineScope.launch {
                delay(2000)
                backToOutApp.value = false
            }
        }
    }

    val navController = rememberNavController()

    Box(modifier = modifier) {
        NavHost(navController = navController, startDestination = Scenes.Login.route) {
            composable(Scenes.Login.route) {
                LoginScene(navController)
            }
            composable(Scenes.Home.route) {
                HomeScene()
            }
            composable(Scenes.SignIn.route) {
                SignInScene(navController)
            }
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