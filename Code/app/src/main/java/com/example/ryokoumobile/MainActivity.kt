package com.example.ryokoumobile

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyNavigationBar
import com.example.ryokoumobile.view.components.MyShowToast
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.scenes.AccountScene
import com.example.ryokoumobile.view.scenes.FavoriteScene
import com.example.ryokoumobile.view.scenes.HomeScene
import com.example.ryokoumobile.view.scenes.LoginScene
import com.example.ryokoumobile.view.scenes.MyTourScene
import com.example.ryokoumobile.view.scenes.SearchScene
import com.example.ryokoumobile.view.scenes.SignInScene
import com.example.ryokoumobile.viewmodel.TourViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            RyokouMobileTheme {
                MainScene()
            }
        }
    }
}

@Composable
fun MainScene() {
//    val tourViewModel = TourViewModel()

    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val displayNavBar = when (currentRoute) {
        Scenes.MainGroup.Home.route -> true
        Scenes.MainGroup.Search.route -> true
        Scenes.MainGroup.Favorite.route -> true
        Scenes.MainGroup.MyTour.route -> true
        Scenes.MainGroup.Account.route -> true
        else -> false
    }

    BackHandleSpam()

    Scaffold(topBar = { MyTopBar() },
        bottomBar = {
            if (displayNavBar) {
                MyNavigationBar(navController)
            }
        })
    { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHost(navController = navController, startDestination = Scenes.MainGroup.route) {
            navigation(
                route = Scenes.AuthGroup.route,
                startDestination = Scenes.AuthGroup.Login.route
            ) {
                composable(Scenes.AuthGroup.Login.route) {
                    LoginScene(modifier, navController)
                }
                composable(Scenes.AuthGroup.SignIn.route) {
                    SignInScene(modifier, navController)
                }
            }
            navigation(
                route = Scenes.MainGroup.route,
                startDestination = Scenes.MainGroup.Home.route
            ) {
                composable(Scenes.MainGroup.Home.route) {
                    HomeScene(
                        modifier = modifier,
                        navController,
                        tourVM = DataController.tourVM
                    )
                }
                composable(Scenes.MainGroup.Search.route) {
                    SearchScene(
                        modifier = modifier,
                        navController
                    )
                }
                composable(Scenes.MainGroup.Favorite.route) {
                    FavoriteScene(
                        modifier = modifier,
                        navController
                    )
                }
                composable(Scenes.MainGroup.MyTour.route) {
                    MyTourScene(
                        modifier = modifier,
                        navController
                    )
                }
                composable(Scenes.MainGroup.Account.route) {
                    AccountScene(
                        modifier = modifier,
                        navController
                    )
                }

            }
        }
    }
}

@Composable
fun BackHandleSpam() {
    val context = LocalContext.current
    val backToOutApp = remember { MutableStateFlow(false) }

    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if (backToOutApp.value) {
            (context as? Activity)?.finish()
        } else {
            backToOutApp.value = true
            MyShowToast(context, "Press back again to exit")
            coroutineScope.launch {
                delay(2000)
                backToOutApp.value = false
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    RyokouMobileTheme {
        MainScene()
    }
}