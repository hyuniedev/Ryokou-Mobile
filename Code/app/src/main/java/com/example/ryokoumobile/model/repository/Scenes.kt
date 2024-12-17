package com.example.ryokoumobile.model.repository

sealed class Scenes(val route: String) {
    object Login : Scenes("Login")
    object SignIn : Scenes("SignIn")
    object Home : Scenes("Home")
}