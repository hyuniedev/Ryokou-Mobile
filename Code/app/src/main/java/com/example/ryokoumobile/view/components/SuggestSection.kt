package com.example.ryokoumobile.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.enumClass.EMonth
import com.example.ryokoumobile.model.enumClass.EProvince
import com.example.ryokoumobile.view.items.ItemCapsule
import com.example.ryokoumobile.view.items.ItemTour

@Composable
fun <T> SuggestSection(
    title: String,
    selected: T,
    lsItemCapsule: List<T>,
    lsTour: List<Tour>,
    onChange: () -> Unit,
    onClickFavorite: () -> Unit
) {
    Column {
        Text(title)
        LazyRow {
            items(lsItemCapsule) {
                ItemCapsule(
                    if (it is EMonth) it.monthName else (it as EProvince).nameProvince,
                    selected == it
                ) { onChange() }
            }
        }
        LazyRow {
//            items(lsTour) { tour ->
//                ItemTour(tour, onClick = onChange, onClickFavorite = onClickFavorite)
//            }
        }
    }
}