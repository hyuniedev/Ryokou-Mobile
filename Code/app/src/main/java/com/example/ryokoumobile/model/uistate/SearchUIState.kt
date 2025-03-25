package com.example.ryokoumobile.model.uistate

import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Tour

data class SearchUIState(
    var isShowSearchSection: Boolean = true,
    var searchText: String = "",
    var selectedCompany: Company = Company(name = "Tất cả"),
    var selectedProvince: String = "Tất cả",
    var priceRange: ClosedFloatingPointRange<Float> = 0f..50f,
    var isDropCompany: Boolean = false,
    var isDropProvince: Boolean = false,
    var lsCompany: List<Company> = listOf(),
    var lsResult: List<Tour> = listOf(),
    var aProvince: Boolean = true,
    var multiProvince: Boolean = true,
    var showResult: Boolean = false
)
