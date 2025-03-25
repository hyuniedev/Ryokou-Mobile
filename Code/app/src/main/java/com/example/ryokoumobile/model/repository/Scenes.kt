package com.example.ryokoumobile.model.repository

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.ryokoumobile.R

sealed class Scenes(
    val route: String,
    var icon: ImageVector = Icons.Outlined.Info,
    var iconOnSelected: ImageVector = Icons.Filled.Info
) {
    data object Notification : Scenes("Notification")
    data object TourDetail : Scenes("TourDetail/{tourId}")
    data object TourPay : Scenes("TourPay/{numTicket}/{dayStart}/{tourId}")
    data object AccountGroup : Scenes("AccountGroup") {
        data object InfoUser : Scenes("InfoUser")
        data object ChangePassword : Scenes("ChangePassword")
    }

    data object AuthGroup : Scenes("AuthGroup") {
        data object Login : Scenes("Login")
        data object SignIn : Scenes("SignIn")

        fun getScenes(): List<Scenes> = listOf(Login, SignIn)
    }

    data object MainGroup : Scenes("MainGroup") {
        data object Home : Scenes("Home", Icons.Outlined.Home, Icons.Filled.Home)
        data object Search : Scenes("Search", Icons.Outlined.Search, Icons.Filled.Search)
        data object Favorite :
            Scenes("Favorite", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)

        data object MyTour : Scenes("MyTour")
        data object Account :
            Scenes("Account", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle)

        fun getScenes(): List<Scenes> = listOf(Home, Search, Favorite, MyTour, Account)
    }
}