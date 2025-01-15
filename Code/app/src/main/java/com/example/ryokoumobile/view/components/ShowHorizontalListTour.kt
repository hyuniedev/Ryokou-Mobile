package com.example.ryokoumobile.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.view.items.ItemTour

@Composable
fun ShowHorizontalListTour(
    lsTour: List<Tour>,
    onClick: (tour: Tour) -> Unit,
    onClickFavorite: (tour: Tour) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(lsTour) { tour ->
            ItemTour(
                tour,
                onClick = { onClick(tour) },
                isFavorite = DataController.user.value?.lsFavoriteTour?.contains(tour.id)
                    ?: false,
                onClickFavorite = { onClickFavorite(tour) }
            )
        }
    }
}