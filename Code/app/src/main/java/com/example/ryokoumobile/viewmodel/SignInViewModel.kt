package com.example.ryokoumobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.entity.ESex
import com.example.ryokoumobile.model.uistate.SignInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState : StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun updateName(newValue: String){
        _uiState.update { it.copy(name = newValue) }
    }
    fun updateEmail(newValue: String){
        _uiState.update { it.copy(email = newValue) }
    }
    fun updateSex(newValue: ESex){
        _uiState.update { it.copy(sex = newValue) }
    }
    fun updateNumberPhone(newValue: String){
        _uiState.update { it.copy(numberPhone = newValue) }
    }
    fun updatePassword(newValue: String){
        _uiState.update { it.copy(password = newValue) }
    }
    fun updatePasswordConfirm(newValue: String){
        _uiState.update { it.copy(passwordConfirm = newValue) }
    }
    fun signIn(){

    }
    fun signInWithGG(){

    }
}