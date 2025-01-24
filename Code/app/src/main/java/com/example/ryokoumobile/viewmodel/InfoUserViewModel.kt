package com.example.ryokoumobile.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.enumClass.ESex
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.model.uistate.InfoUserUIState
import com.example.ryokoumobile.view.components.MyShowToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InfoUserUIState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                fullname = DataController.user.value!!.fullName,
                numberPhone = DataController.user.value!!.numberPhone,
                email = DataController.user.value!!.email,
                sex = DataController.user.value!!.sex
            )
        }
    }

    fun updateFullName(value: String) {
        _uiState.update { it.copy(fullname = value) }
    }

    fun updateNumberPhone(value: String) {
        _uiState.update { it.copy(numberPhone = value) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun updateSex(value: ESex) {
        _uiState.update { it.copy(sex = value) }
    }

    fun updateIsEditing(context: Context) {
        if (_uiState.value.isEditing && !checkField(context)) return
        _uiState.update { it.copy(isEditing = !it.isEditing) }
        if (!_uiState.value.isEditing) {
            saveData()
        }
    }

    private fun saveData() {
        viewModelScope.launch {
            DataController.user.update {
                it?.copy(
                    fullName = _uiState.value.fullname,
                    numberPhone = _uiState.value.numberPhone,
                    email = _uiState.value.email,
                    sex = _uiState.value.sex
                )
            }
            if (DataController.user.value != null) {
                FirebaseController.firestore.collection("users")
                    .document(DataController.user.value!!.id!!).set(DataController.user.value!!)
            }
        }
    }

    private fun checkField(context: Context): Boolean {
        if (_uiState.value.fullname.isBlank()) {
            _uiState.update {
                it.copy(isFullNameError = true)
            }
            MyShowToast(context, "Please enter your full name")
            return false
        }
        if (_uiState.value.email.isBlank() || !checkFormatEmail(_uiState.value.email)) {
            _uiState.update {
                it.copy(isEmailError = true)
            }
            MyShowToast(context, "Email is blank or incorrectly formatted")
            return false
        }
        if (_uiState.value.sex == ESex.None) {
            _uiState.update { it.copy(isSexError = true) }
            MyShowToast(context, "Please select your gender")
            return false
        }
        if (_uiState.value.numberPhone.isBlank() || !checkPhoneNumberValid(_uiState.value.numberPhone)) {
            _uiState.update { it.copy(isNumberPhoneError = true) }
            MyShowToast(context, "Number phone is blank or incorrectly formatted")
            return false
        }
        return true
    }

    private fun checkFormatEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return email.matches(emailRegex.toRegex())
    }

    private fun checkPhoneNumberValid(phone: String): Boolean {
        val phoneRegex = "^\\+?[0-9]{10,15}$"
        return phone.matches(phoneRegex.toRegex())
    }

    fun logout(navController: NavController) {
        viewModelScope.launch {
            navController.navigate(Scenes.MainGroup.Home.route)
            FirebaseController.SignOut()
        }
    }
}