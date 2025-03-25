package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.UserAnalytics
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.RecommendedTours
import com.example.ryokoumobile.view.components.ShowGridTour

@Composable
fun FavoriteScene(modifier: Modifier = Modifier, navController: NavController) {
    val userState = DataController.user.collectAsState()

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (userState.value) {
            null -> OnNotLoggedIn(navController)
            else -> OnLoggedIn(userState.value!!, navController)
        }
    }
}

@Composable
private fun OnNotLoggedIn(navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.LightGray.copy(alpha = 0.15f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyElevatedButton(
                Modifier
                    .weight(0.3f)
                    .padding(horizontal = 20.dp),
                "Đăng nhập",
                isFilled = false
            ) {
                navController.navigate(
                    Scenes.AuthGroup.Login.route
                )
            }
            MyElevatedButton(
                Modifier
                    .weight(0.3f)
                    .padding(
                        horizontal = 20.dp
                    ),
                "Đăng ký"
            ) { navController.navigate(Scenes.AuthGroup.SignIn.route) }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NotifyOnFavoriteEmpty()
        }
    }
}

@Composable
private fun NotifyOnFavoriteEmpty() {
    val textStyle = TextStyle(
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
    Image(
        painterResource(R.drawable.favo1),
        contentDescription = null,
        modifier = Modifier.size(200.dp)
    )
    Spacer(Modifier.height(15.dp))
    Text(stringResource(R.string.favoriteListIsEmpty), style = textStyle)
    Spacer(Modifier.height(15.dp))
    Text(stringResource(R.string.exploreAndChoose), style = textStyle)
}

@Composable
private fun OnLoggedIn(user: User, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth(0.98f)
            .padding(horizontal = 15.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(Modifier.height(15.dp))
        Text(
            stringResource(R.string.yourFavoriteTour),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.primary
            )
        )
        if (user.lsFavoriteTour.mapNotNull { DataController.tourVM.getTourFromID(it) }
                .isNotEmpty()) {
            ShowGridTour(
                navController = navController,
                lsTour = user.lsFavoriteTour.mapNotNull { DataController.tourVM.getTourFromID(it) })
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NotifyOnFavoriteEmpty()
            }
        }
        Spacer(Modifier.height(10.dp))
        RecommendedTours(UserAnalytics.lsSimilarTour.subList(0, 10), navController)
    }
}