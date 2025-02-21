package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.UserAnalytics
import com.example.ryokoumobile.model.enumClass.EProvince
import com.example.ryokoumobile.model.uistate.SearchUIState
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.RecommendedTours
import com.example.ryokoumobile.view.components.ShowGridTour
import com.example.ryokoumobile.viewmodel.SearchViewModel

@Composable
fun SearchScene(
    modifier: Modifier = Modifier,
    navController: NavController,
    searchVM: SearchViewModel = viewModel()
) {
    val uiState = searchVM.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val focusState = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .clickable(interactionSource = null, indication = null) {
                focusState.clearFocus()
            }
    ) {
        SearchBox(searchVM = searchVM, uiState = uiState.value, focus = focusState)
        SearchResult(uiState = uiState.value, navController = navController)
    }
}

@Composable
private fun SearchResult(
    modifier: Modifier = Modifier,
    uiState: SearchUIState,
    navController: NavController,
) {
    Column(modifier.padding(horizontal = 15.dp)) {
        if (uiState.lsResult.isEmpty()) {
            RecommendedTours(UserAnalytics.lsMostPopularTour, navController)
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Kết quả tìm kiếm:",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(
                    "(${uiState.lsResult.size} tours)",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.LightGray
                    )
                )
            }
            ShowGridTour(
                uiState.lsResult,
                navController
            ) { tour -> DataController.tourVM.navigationToTourDetail(navController, tour) }
        }
    }
}

@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    searchVM: SearchViewModel,
    uiState: SearchUIState,
    focus: FocusManager
) {
    var lsProvince = listOf("Tất cả")
    EProvince.entries.forEach {
        lsProvince = lsProvince + it.nameProvince
    }
    var lsPriceRange = listOf(
        "Tất cả",
        "0 VNĐ - 5.000.000 VNĐ",
        "5.000.000 VNĐ - 10.000.000 VNĐ",
        "10.000.000 VNĐ - 15.000.000 VNĐ",
        "15.000.000 VNĐ - 20.000.000 VNĐ",
        "20.000.000 VNĐ - 25.000.000 VNĐ",
        "25.000.000 VNĐ - 30.000.000 VNĐ",
        "Từ 30.000.00 VNĐ trở lên"
    )
    Box(modifier = modifier.padding(horizontal = 15.dp, vertical = 20.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.searchText,
                onValueChange = { value -> searchVM.updateSearchText(value) },
                label = { Text("Nhập từ khóa tìm kiếm") }, singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { searchVM.updateIsDropCompany(true) },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.home_work),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        uiState.selectedCompany.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .heightIn(max = 250.dp),
                        expanded = uiState.isDropCompany,
                        onDismissRequest = { searchVM.updateIsDropCompany(false) }) {
                        uiState.lsCompany.forEach { company ->
                            DropdownMenuItem(
                                onClick = { searchVM.updateSelectedCompany(company) },
                                text = { Text(company.name) })
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { searchVM.updateIsDropProvince(true) },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        uiState.selectedProvince,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .heightIn(max = 250.dp),
                        expanded = uiState.isDropProvince,
                        onDismissRequest = { searchVM.updateIsDropProvince(false) }) {
                        lsProvince.forEach { value ->
                            DropdownMenuItem(
                                onClick = { searchVM.updateSelectedProvince(value) },
                                text = { Text(value) })
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { searchVM.updateIsDropPriceRange(true) },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.price_outline),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    uiState.priceRange,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                )
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .heightIn(max = 250.dp),
                    expanded = uiState.isDropPriceRange,
                    onDismissRequest = { searchVM.updateIsDropPriceRange(false) }) {
                    lsPriceRange.forEach { range ->
                        DropdownMenuItem(
                            onClick = { searchVM.updateSelectedPriceRange(range) },
                            text = { Text(range) })
                    }
                }
            }
            MyElevatedButton(
                title = "Tìm kiếm",
                isFilled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                searchVM.toursFilter()
                focus.clearFocus()
            }
        }
    }
}
