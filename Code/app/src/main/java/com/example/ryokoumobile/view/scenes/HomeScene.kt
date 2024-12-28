package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val stateScroll = rememberScrollState()

    Scaffold(topBar = { MyTopBar() }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 10.dp)
                .verticalScroll(stateScroll)
        ) {
            MyHorizontalPage(
                lsLinkPicture = lsPager
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                RangeTimeScale(uiState.value.startTime)
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .height(350.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    FlashSaleTimeCountDown(uiState.value.duration)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(DataController.lsTour) { tour ->
                            ItemTour(tour, {}, {})
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { }) {
                            Text(
                                "More",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
//                SuggestSection()
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