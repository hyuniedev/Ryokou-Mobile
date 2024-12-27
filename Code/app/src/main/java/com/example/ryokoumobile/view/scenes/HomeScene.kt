package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.repository.lsPager
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.FlashSaleTimeCountDown
import com.example.ryokoumobile.view.components.MyHorizontalPage
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.components.RangeTimeScale
import com.example.ryokoumobile.view.items.ItemTour
import com.example.ryokoumobile.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import java.time.LocalTime

@Composable
fun HomeScene(viewModel: HomeViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            viewModel.updateTime(LocalTime.now())
            viewModel.updateDuration()
        }
    }

    Scaffold(topBar = { MyTopBar() }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 10.dp)
        ) {
            MyHorizontalPage(
                lsLinkPicture = lsPager
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                RangeTimeScale(uiState.value.startTime)
                FlashSaleTimeCountDown(uiState.value.duration)
                LazyRow {
                    items(DataController.lsTour) { tour ->
                        ItemTour(tour, {})
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScenePreview() {
    RyokouMobileTheme {
        HomeScene()
    }
}