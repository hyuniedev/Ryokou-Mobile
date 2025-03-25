package com.example.ryokoumobile.view.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.view.items.ItemBookedTour
import com.example.ryokoumobile.view.items.ItemTour

@Composable
fun <T> ShowGridTour(
    lsTour: List<T>,
    navController: NavController? = null,
    onClick: (T) -> Unit = {}
) {
    val tours = DataController.tourVM
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        lsTour.chunked(2).forEach {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                it.forEach { tour ->
                    when (tour) {
                        is Tour -> {
                            ItemTour(
                                tour = tour,
                                isFavorite = tours.getIsFavoriteTour(tour),
                                onClick = {
                                    DataController.tourVM.navigationToTourDetail(
                                        navController = navController!!,
                                        tour = tour
                                    )
                                },
                                onClickFavorite = { DataController.updateFavoriteTour(tour) })
                        }

                        is TourBooked -> {
                            ItemBookedTour(bookedTour = tour) { onClick(tour) }
                        }

                        else -> {}
                    }
                    if (it.size == 1) {
                        Spacer(Modifier.width(170.dp))
                    }
                }
            }
        }
    }
}