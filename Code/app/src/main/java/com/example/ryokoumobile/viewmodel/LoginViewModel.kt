package com.example.ryokoumobile.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.uistate.LoginUiState
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email, emailError = false) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, passwordError = false) }
    }

     fun login() {
        viewModelScope.launch {
            if(_uiState.value.email.isBlank() || _uiState.value.password.isBlank()){
                _uiState.update {
                    it.copy(isLoading = false, emailError = true, passwordError = true)
                }
                return@launch
            }
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                FirebaseController.LoginWithEmailAndPassword(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                ).await()
                _uiState.value.isLoginSuccessful = true
            }catch (e: Exception){
                Log.e("HyuNie","Error: " + e.message)
                e.printStackTrace()
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun loginWithGG(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if(FirebaseController.LoginWithGoogleAccount(context)){
                    _uiState.update { it.copy(isLoading = false, isLoginSuccessful = true) }
                }else{
                    Log.e("HyuNie","Have any bug when login with gg account")
                }
            }catch (e: Exception){
                Log.e("HyuNie","Error: " + e.message)
            }
        }
    }

    fun forgetPassword() {

    }

    fun signIn() {

    }

}