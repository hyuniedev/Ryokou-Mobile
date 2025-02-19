package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.uistate.SearchUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitCompanyList()
    }

    fun updateSearchText(value: String) {
        _uiState.update { it.copy(searchText = value) }
    }

    fun updateIsDropProvince(value: Boolean) {
        _uiState.update { it.copy(isDropProvince = value) }
    }

    fun updateIsDropCompany(value: Boolean) {
        _uiState.update { it.copy(isDropCompany = value) }
    }

    fun updateIsDropPriceRange(value: Boolean) {
        _uiState.update { it.copy(isDropPriceRange = value) }
    }

    fun updateSelectedCompany(company: Company) {
        _uiState.update { it.copy(selectedCompany = company, isDropCompany = false) }
    }

    fun updateSelectedProvince(province: String) {
        _uiState.update { it.copy(selectedProvince = province, isDropProvince = false) }
    }

    fun updateSelectedPriceRange(priceRange: String) {
        _uiState.update { it.copy(priceRange = priceRange, isDropPriceRange = false) }
    }

    fun toursFilter() {
        _uiState.update { it.copy(lsResult = listOf()) }
        var lsTour = DataController.tourVM.uiState.value
        var lsPrice = _uiState.value.priceRange.replace(Regex("[^0-9-]"), "").split("-")
            .mapNotNull { it.toLongOrNull() }
        lsTour.forEach { tour ->
            if (checkKeyword(tour) && checkCompany(tour) && checkLocation(tour) && checkPrice(
                    tour,
                    lsPrice
                )
            ) {
                _uiState.update { it.copy(lsResult = it.lsResult + tour) }
            }
        }
    }

    private fun checkPrice(tour: Tour, lsPrice: List<Long>): Boolean {
        val price = _uiState.value.priceRange
        if (price == "Tất cả") return true

        if (lsPrice.size != 2) {
            return tour.getPriceWithUnformatted() > 30000000
        } else {
            return lsPrice[0] <= tour.getPriceWithUnformatted() && lsPrice[1] >= tour.getPriceWithUnformatted()
        }
    }

    private fun checkLocation(tour: Tour): Boolean {
        if (_uiState.value.selectedProvince == "Tất cả") return true
        return tour.city.contains(_uiState.value.selectedProvince)
    }

    private fun checkCompany(tour: Tour): Boolean {
        if (_uiState.value.selectedCompany.id.isEmpty()) return true
        return tour.company == _uiState.value.selectedCompany.id
    }

    private fun checkKeyword(tour: Tour): Boolean {
        return tour.name.lowercase().contains(_uiState.value.searchText.lowercase())
    }

    private fun loadInitCompanyList() {
        viewModelScope.launch {
            _uiState.update { it.copy(lsCompany = it.lsCompany + Company(name = "Tất cả")) }
            FirebaseController.firestore.collection("companys")
                .get()
                .addOnSuccessListener { result ->
                    try {
                        for (doc in result) {
                            val company = doc.toObject(Company::class.java)
                            _uiState.update { it.copy(lsCompany = it.lsCompany + company) }
                        }
                    } catch (e: Exception) {
                        Log.e("HyuNie", "${e.message}")
                    }
                }
        }
    }
}