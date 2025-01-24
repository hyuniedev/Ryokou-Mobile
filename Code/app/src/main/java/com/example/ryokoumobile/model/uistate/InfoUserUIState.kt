package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.enumClass.ESex

data class InfoUserUIState(
    var fullname: String = "",
    var isFullNameError: Boolean = false,
    var numberPhone: String = "",
    var isNumberPhoneError: Boolean = false,
    var email: String = "",
    var isEmailError: Boolean = false,
    var sex: ESex = ESex.None,
    var isSexError: Boolean = false,
    var isEditing: Boolean = false
)
