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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.MyNavigationBar
import com.example.ryokoumobile.view.components.MyShowToast
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.scenes.AccountScene
import com.example.ryokoumobile.view.scenes.FavoriteScene
import com.example.ryokoumobile.view.scenes.HomeScene
import com.example.ryokoumobile.view.scenes.InfoUserScene
import com.example.ryokoumobile.view.scenes.LoginScene
import com.example.ryokoumobile.view.scenes.MyTourScene
import com.example.ryokoumobile.view.scenes.SearchScene
import com.example.ryokoumobile.view.scenes.SignInScene
import com.example.ryokoumobile.view.scenes.TourDetail
import com.example.ryokoumobile.view.scenes.TourPay
import com.example.ryokoumobile.viewmodel.TourViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

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
    val displayTopBar = when (currentRoute) {
        Scenes.TourDetail.route -> false
        else -> true
    }

    BackHandleSpam()

    Scaffold(topBar = { if (displayTopBar) MyTopBar() },
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
            composable(
                route = Scenes.TourDetail.route,
                arguments = listOf(navArgument("tourId") { type = NavType.StringType })
            ) { backStackEntry ->
                val tour =
                    DataController.tourVM.getTourFromID(
                        backStackEntry.arguments?.getString("tourId") ?: ""
                    )
                TourDetail(tour, navController)
            }
            composable(
                route = Scenes.TourPay.route,
                arguments = listOf(
                    navArgument("numTicket") { type = NavType.IntType },
                    navArgument("dayStart") { type = NavType.LongType },
                    navArgument("idTour") { type = NavType.StringType })
            ) { backStackEntry ->
                // Lấy giá trị `dayStart` từ arguments
                val dayStartSeconds = backStackEntry.arguments?.getLong("dayStart")

                // Xử lý trường hợp `null` và chuyển đổi sang mili giây
                val dayStartMillis = when {
                    dayStartSeconds != null -> dayStartSeconds * 1000 // Chuyển đổi seconds sang milliseconds
                    else -> System.currentTimeMillis() // Sử dụng thời gian hiện tại nếu `dayStart` là null
                }

                val tourBooked = TourBooked(
                    numPerson = backStackEntry.arguments?.getInt("numTicket") ?: 1,
                    startDay = Timestamp(Date(dayStartMillis)),
                    idTour = backStackEntry.arguments?.getString("idTour") ?: ""
                )
                TourPay(tourBooked, navController)
            }
            navigation(
                route = Scenes.AccountGroup.route,
                startDestination = Scenes.AccountGroup.InfoUser.route
            ) {
                composable(Scenes.AccountGroup.InfoUser.route) {
                    InfoUserScene()
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