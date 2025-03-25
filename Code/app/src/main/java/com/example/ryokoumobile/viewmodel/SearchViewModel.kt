package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.uistate.SearchUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToLong

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

    fun updateRangePrice(value: ClosedFloatingPointRange<Float>) {
        _uiState.update {
            it.copy(
                priceRange = value.start.roundToLong().toFloat()..value.endInclusive.roundToLong()
                    .toFloat()
            )
        }
    }

    fun updateSelectedCompany(company: Company) {
        _uiState.update { it.copy(selectedCompany = company, isDropCompany = false) }
    }

    fun updateSelectedProvince(province: String) {
        _uiState.update { it.copy(selectedProvince = province, isDropProvince = false) }
    }

    fun updateAProvince() {
        _uiState.update { it.copy(aProvince = !it.aProvince) }
        if (!_uiState.value.aProvince && !_uiState.value.multiProvince)
            updateMultiProvince()
    }

    fun updateMultiProvince() {
        _uiState.update { it.copy(multiProvince = !it.multiProvince) }
        if (!_uiState.value.aProvince && !_uiState.value.multiProvince)
            updateAProvince()
    }

    private fun updateShowResult() {
        _uiState.update { it.copy(showResult = true) }
    }

    fun toursFilter() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { it.copy(lsResult = listOf()) }
            val lsTour = DataController.tourVM.uiState.value
            lsTour.forEach { tour ->
                if (checkKeyword(tour) && checkCompany(tour) && checkLocation(tour) && checkPrice(
                        tour
                    ) && checkTypeTour(tour)
                ) {
                    _uiState.update { it.copy(lsResult = it.lsResult + tour) }
                }
            }
            updateShowResult()
        }
    }

    private fun checkTypeTour(tour: Tour): Boolean {
        if (_uiState.value.aProvince && _uiState.value.multiProvince)
            return true
        var okCheck = true
        if (_uiState.value.aProvince && tour.city.size > 1) okCheck = false
        if (_uiState.value.multiProvince && tour.city.size == 1) okCheck = false
        return okCheck
    }

    private fun checkPrice(tour: Tour): Boolean {
        if (uiState.value.priceRange.endInclusive == 51f) return tour.getPriceWithUnformatted() > uiState.value.priceRange.start * 1000000
        return tour.getPriceWithUnformatted()
            .toFloat() in uiState.value.priceRange.start * 1000000..uiState.value.priceRange.endInclusive * 1000000
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

    fun getFormatPrice(price: Long): String {
        return if (price == 51L) "Hơn ${price - 1} triệu VNĐ" else "$price triệu VNĐ"
    }

    private fun loadInitCompanyList() {
        viewModelScope.launch {
            _uiState.update { it.copy(lsCompany = it.lsCompany + Company(name = "Tất cả")) }
            FirebaseController.firestore.collection("companys")
                .whereEqualTo("active", true)
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