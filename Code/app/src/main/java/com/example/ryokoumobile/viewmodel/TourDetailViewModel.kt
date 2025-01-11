package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.uistate.TourDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TourDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TourDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCompanyWithId(idCompany: String) {
        viewModelScope.launch {
            FirebaseController.firestore.collection("companys").document(idCompany).get()
                .addOnSuccessListener {
                    if (it != null && it.exists()) {
                        val company = it.toObject(Company::class.java)
                        if (company != null) {
                            _uiState.update { it.copy(companyName = company.name) }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("HyuNie", "Bug on load company: ${it.message}")
                }
        }
    }
}