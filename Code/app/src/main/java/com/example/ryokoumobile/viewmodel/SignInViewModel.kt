package com.example.ryokoumobile.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.ESex
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.uistate.SignInUiState
import com.example.ryokoumobile.view.components.MyShowToast
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun updateName(newValue: String) {
        _uiState.update { it.copy(name = newValue, nameError = false) }
    }

    fun updateEmail(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = false) }
    }

    fun updateSex(newValue: ESex) {
        _uiState.update { it.copy(sex = newValue, sexError = false) }
    }

    fun updateNumberPhone(newValue: String) {
        _uiState.update { it.copy(numberPhone = newValue, numberPhoneError = false) }
    }

    fun updatePassword(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = false) }
    }

    fun updatePasswordConfirm(newValue: String) {
        _uiState.update { it.copy(passwordConfirm = newValue, passwordConfirmError = false) }
    }

    fun updateCheckBoxService() {
        _uiState.update { it.copy(cbService = !_uiState.value.cbService) }
    }

    fun signIn(context: Context) {
        _uiState.update { it.copy(isLoading = true) }
        if (!checkFields(context)) return
        if (_uiState.value.cbService) {
            val user = User(
                email = _uiState.value.email,
                password = _uiState.value.password,
                sex = _uiState.value.sex,
                numberPhone = _uiState.value.numberPhone,
                fullName = _uiState.value.name
            )
            viewModelScope.launch {
                FirebaseController.SignIn(user).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        DataController.user = user
                        _uiState.update { it.copy(isSignInSuccess = true) }
                    } else {
                        when (task.exception) {
                            is FirebaseAuthUserCollisionException -> MyShowToast(
                                context,
                                "This email is already registered!"
                            )
                            else -> MyShowToast(
                                context,
                                task.exception?.message ?: "Registration failed."
                            )
                        }
                    }
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        } else {
            MyShowToast(
                context,
                "Please agree to the Terms of Service & Privacy Policy before continuing."
            )
        }
    }

    private fun checkFields(context: Context): Boolean {
        if (_uiState.value.name.isBlank()) {
            _uiState.update {
                it.copy(nameError = true)
            }
            MyShowToast(context, "Please enter your full name")
            return false
        }
        if (_uiState.value.email.isBlank() || !checkFormatEmail(_uiState.value.email)) {
            _uiState.update {
                it.copy(emailError = true)
            }
            MyShowToast(context, "Email is blank or incorrectly formatted")
            return false
        }
        if (_uiState.value.sex == ESex.None) {
            _uiState.update { it.copy(sexError = true) }
            MyShowToast(context, "Please select your gender")
            return false
        }
        if (_uiState.value.numberPhone.isBlank() || !checkPhoneNumberValid(_uiState.value.numberPhone)) {
            _uiState.update { it.copy(numberPhoneError = true) }
            MyShowToast(context, "Number phone is blank or incorrectly formatted")
            return false
        }
        if (_uiState.value.password.isBlank() || _uiState.value.password.length < 6) {
            _uiState.update { it.copy(passwordError = true) }
            MyShowToast(context, "Password must be longer than 6 characters")
            return false
        }
        if (_uiState.value.passwordConfirm != _uiState.value.password) {
            _uiState.update { it.copy(passwordConfirmError = true) }
            MyShowToast(context, "Password confirm is incorrect")
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


    fun signInWithGG(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if(FirebaseController.isSignedIn()){
                    FirebaseController.SignOut()
                    _uiState.update { it.copy(isLoading = false) }
                }
                if(FirebaseController.LoginWithGoogleAccount(context)){
                    _uiState.update { it.copy(isLoading = false, isSignInSuccess = true) }
                }else{
                    _uiState.update { it.copy(isLoading = false) }
                }
            }catch (e: Exception){
                _uiState.update { it.copy(isLoading = true) }
                e.printStackTrace()
            }
        }
    }
}