package com.example.ryokoumobile.model.uistate

data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var isLoading: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var isLoginSuccessful : Boolean = false
)
