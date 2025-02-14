package com.example.ryokoumobile.model.uistate

data class ChangePasswordUIState(
    var currentPassword: String = "",
    var newPassword: String = "",
    var confirmNewPassword: String = "",
    var isShowConfirmDialog: Boolean = false,
    var isShowNotPasswordAccountDialog: Boolean = false,
    var isCompleted: Boolean = false,
    var isCurrentPasswordError: Boolean = false,
    var isNewPasswordError: Boolean = false,
    var isConfirmPasswordError: Boolean = false
)