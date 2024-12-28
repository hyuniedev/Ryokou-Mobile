package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.enumClass.ESex

data class SignInUiState(
    var name: String = "",
    var email: String = "",
    var sex: ESex = ESex.None,
    var numberPhone: String = "",
    var password: String = "",
    var passwordConfirm: String = "",
    var cbService: Boolean = false,
    var nameError: Boolean = false,
    var emailError: Boolean = false,
    var sexError: Boolean = false,
    var numberPhoneError: Boolean = false,
    var passwordError: Boolean = false,
    var passwordConfirmError: Boolean = false,
    var isLoading: Boolean = false,
    var isSignInSuccess: Boolean = false
)