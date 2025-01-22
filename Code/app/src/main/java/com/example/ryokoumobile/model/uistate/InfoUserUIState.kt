package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.enumClass.ESex

data class InfoUserUIState(
    var fullname: String = "",
    var numberphone: String = "",
    var email: String = "",
    var sex: ESex = ESex.None,
    var isEditing: Boolean = false
)
