package com.example.ryokoumobile.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.uistate.ChangePasswordUIState
import com.example.ryokoumobile.view.components.MyShowToast
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChangePasswordUIState())
    val uiState = _uiState.asStateFlow()

    fun updateCurrentPassword(value: String) {
        _uiState.update { it.copy(currentPassword = value, isCurrentPasswordError = false) }
    }

    fun updateNewPassword(value: String) {
        _uiState.update {
            it.copy(
                newPassword = value,
                isNewPasswordError = false,
                isConfirmPasswordError = false
            )
        }
    }

    fun updateConfirmNewPassword(value: String) {
        _uiState.update {
            it.copy(
                confirmNewPassword = value,
                isConfirmPasswordError = false,
                isNewPasswordError = false
            )
        }
    }

    fun offShowNotPasswordAccountDialog() {
        _uiState.update { it.copy(isShowNotPasswordAccountDialog = false) }
    }

    fun offConfirmDialog() {
        _uiState.update { it.copy(isShowConfirmDialog = false) }
    }

    fun offCompletedDialog() {
        _uiState.update { it.copy(isCompleted = false) }
    }

    fun onClickConfirmDialog() {
        viewModelScope.launch {
            FirebaseController.auth.currentUser!!.updatePassword(_uiState.value.newPassword)
                .addOnCompleteListener {
                    _uiState.update { it.copy(isCompleted = true) }
                }
            _uiState.update { it.copy(isShowConfirmDialog = false) }
        }
    }

    fun onClickConfirmButton(context: Context) {
        viewModelScope.launch {
            if (_uiState.value.currentPassword.isEmpty()) {
                _uiState.update { it.copy(isCurrentPasswordError = true) }
                MyShowToast(context, "Vui lòng nhập mật khẩu hiện tại.")
                return@launch
            }
            val user = FirebaseController.auth.currentUser
            if (user != null && user.providerData.map { it.providerId }.contains("password")) {
                val credential = EmailAuthProvider.getCredential(
                    DataController.user.value!!.email,
                    _uiState.value.currentPassword
                )
                user.reauthenticate(credential).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        _uiState.update { it.copy(isCurrentPasswordError = true) }
                    }
                }
            } else {
                _uiState.update { it.copy(isShowNotPasswordAccountDialog = true) }
            }
            if (_uiState.value.newPassword != _uiState.value.confirmNewPassword) {
                _uiState.update {
                    it.copy(
                        isNewPasswordError = true,
                        isConfirmPasswordError = true
                    )
                }
                MyShowToast(context, "Mật khẩu không trùng khớp.")
                return@launch
            }
            if (_uiState.value.newPassword.length < 6) {
                _uiState.update { it.copy(isNewPasswordError = true) }
                MyShowToast(context, "Mật khẩu phải lớn hơn 6 ký tự.")
                return@launch
            }
            _uiState.update { it.copy(isShowConfirmDialog = true) }
        }
    }
}