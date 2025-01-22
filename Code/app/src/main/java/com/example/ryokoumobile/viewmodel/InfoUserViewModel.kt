package com.example.ryokoumobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.enumClass.ESex
import com.example.ryokoumobile.model.uistate.InfoUserUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InfoUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InfoUserUIState())
    val uiState = _uiState.asStateFlow()

    fun updateFullName(value: String) {
        _uiState.update { it.copy(fullname = value) }
    }

    fun updateNumberPhone(value: String) {
        _uiState.update { it.copy(numberphone = value) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun updateSex(value: ESex) {
        _uiState.update { it.copy(sex = value) }
    }

    fun updateIsEditing() {
        _uiState.update { it.copy(isEditing = !it.isEditing) }
        if (!_uiState.value.isEditing) {
            saveData()
        }
    }

    private fun saveData() {
        DataController.user.update {
            it?.copy(
                fullName = _uiState.value.fullname,
                numberPhone = _uiState.value.numberphone,
                email = _uiState.value.email,
                sex = _uiState.value.sex
            )
        }
    }
}