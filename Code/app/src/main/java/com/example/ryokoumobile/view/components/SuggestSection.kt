package com.example.ryokoumobile.view.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.UserAnalytics
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.enumClass.EMonth
import com.example.ryokoumobile.model.enumClass.EProvince
import com.example.ryokoumobile.view.items.ItemCapsule
import com.example.ryokoumobile.view.items.ItemTour

@Composable
fun SuggestSection(
    title: String,
    lsTour: List<Tour>,
    selected: EProvince,
    lsItemCapsule: List<EProvince>,
    onChange: (item: EProvince) -> Unit,
    onClickFavorite: (tour: Tour) -> Unit,
    onClick: (tour: Tour) -> Unit
) {
    Column(modifier = Modifier.height(365.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            title,
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lsItemCapsule) {
                ItemCapsule(
                    it,
                    selected == it
                ) { onChange(it) }
            }
        }
        ShowHorizontalListTour(
            lsTour.filter { it.city.contains(selected.nameProvince) },
            onClick,
            onClickFavorite
        )
    }
}