package com.example.ryokoumobile.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.entity.ESex
import com.example.ryokoumobile.model.uistate.SignInUiState
import com.example.ryokoumobile.view.components.MyShowToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState : StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun updateName(newValue: String){
        _uiState.update { it.copy(name = newValue, nameError = false) }
    }
    fun updateEmail(newValue: String){
        _uiState.update { it.copy(email = newValue, emailError = false) }
    }
    fun updateSex(newValue: ESex){
        _uiState.update { it.copy(sex = newValue, sexError = false) }
    }
    fun updateNumberPhone(newValue: String){
        _uiState.update { it.copy(numberPhone = newValue, numberPhoneError = false) }
    }
    fun updatePassword(newValue: String){
        _uiState.update { it.copy(password = newValue, passwordError = false) }
    }
    fun updatePasswordConfirm(newValue: String){
        _uiState.update { it.copy(passwordConfirm = newValue, passwordConfirmError = false) }
    }
    fun updateCheckBoxService(){
        _uiState.update { it.copy(cbService = !_uiState.value.cbService) }
    }
    fun signIn(context: Context){
        checkField(context)
        if(_uiState.value.cbService){

        }else{
            MyShowToast(context,"Please agree to the Terms of Service & Privacy Policy before continuing.")
        }
    }

    private fun checkField(context: Context){
        if(_uiState.value.name.isBlank()){
            _uiState.update {
                it.copy(nameError = true)
            }
            MyShowToast(context,"Please enter your full name")
            return
        }
        if(_uiState.value.email.isBlank()||!checkFormatEmail(_uiState.value.email)){
            _uiState.update {
                it.copy(emailError = true)
            }
            MyShowToast(context,"Email is blank or incorrectly formatted")
            return
        }
        if(_uiState.value.sex==ESex.None){
            _uiState.update { it.copy(sexError = true) }
            MyShowToast(context,"Please select your gender")
            return
        }
        if(_uiState.value.numberPhone.isBlank() || !checkPhoneNumberValid(_uiState.value.numberPhone)){
            _uiState.update { it.copy(numberPhoneError = true) }
            MyShowToast(context,"Number phone is blank or incorrectly formatted")
            return
        }
        if(_uiState.value.password.isBlank()||_uiState.value.password.length < 6){
            _uiState.update { it.copy(passwordError = true) }
            MyShowToast(context,"Password must be longer than 6 characters")
            return
        }
        if(_uiState.value.passwordConfirm != _uiState.value.password){
            _uiState.update { it.copy(passwordConfirmError = true) }
            MyShowToast(context,"Password confirm is incorrect")
            return
        }
    }

    private fun checkFormatEmail(email: String): Boolean{
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return email.matches(emailRegex.toRegex())
    }

    private fun checkPhoneNumberValid(phone: String): Boolean {
        val phoneRegex = "^\\+?[0-9]{10,15}$"
        return phone.matches(phoneRegex.toRegex())
    }


    fun signInWithGG(){

    }
}