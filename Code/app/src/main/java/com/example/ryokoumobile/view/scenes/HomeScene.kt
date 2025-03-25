package com.example.ryokoumobile.view.scenes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.UserAnalytics
import com.example.ryokoumobile.model.enumClass.EProvince
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.model.repository.lsPager
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.BoxWelcome
import com.example.ryokoumobile.view.components.FlashSaleTimeCountDown
import com.example.ryokoumobile.view.components.MyHorizontalPage
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.components.RangeTimeScale
import com.example.ryokoumobile.view.components.ShowHorizontalListTour
import com.example.ryokoumobile.view.components.SuggestSection
import com.example.ryokoumobile.view.items.ItemTour
import com.example.ryokoumobile.viewmodel.HomeViewModel
import com.example.ryokoumobile.viewmodel.TourViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun HomeScene(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    tourVM: TourViewModel = viewModel(),
) {
    UserAnalytics.updateMostPopularTour()
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            viewModel.updateTime(LocalDateTime.now())
            viewModel.updateDuration()
        }
    }
    val stateScroll = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(stateScroll)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            if (stateScroll.value == 0) {
                BoxWelcome { navController.navigate(Scenes.AuthGroup.Login.route) }
            }
        }
        Spacer(Modifier.height(10.dp))
        MyHorizontalPage(
            lsLinkPicture = lsPager
        )
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            RangeTimeScale(uiState.value.startTime)
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .height(330.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                FlashSaleTimeCountDown(uiState.value.duration)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        UserAnalytics.lsDealTour.subList(
                            0,
                            if (UserAnalytics.lsDealTour.size < 10) UserAnalytics.lsDealTour.size else 10
                        )
                    ) { tour ->
                        ItemTour(
                            tour,
                            isFavorite = tourVM.getIsFavoriteTour(tour),
                            onClick = {
                                tourVM.navigationToTourDetail(navController, tour)
                            },
                            onClickFavorite = { DataController.updateFavoriteTour(tour) })
                    }
                }
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .border(
//                                width = 1.dp,
//                                color = MaterialTheme.colorScheme.primary,
//                                shape = RoundedCornerShape(20.dp)
//                            )
//                            .clip(RoundedCornerShape(20.dp))
//                            .clickable { }) {
//                        Text(
//                            "Xem thêm",
//                            style = TextStyle(
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 21.sp,
//                                color = MaterialTheme.colorScheme.primary
//                            ),
//                            modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
//                        )
//                    }
//                }
            }
            Spacer(Modifier.height(10.dp))
            SuggestSection(
                title = stringResource(R.string.suggest2),
                lsTour = DataController.tourVM.uiState.collectAsState().value,
                lsItemCapsule = UserAnalytics.getTopProvince(),
                selected = uiState.value.itemSelected2,
                onChange = { item -> viewModel.updateItemSelected2(item) },
                onClickFavorite = { tour -> DataController.updateFavoriteTour(tour) },
                onClick = { tour ->
                    DataController.tourVM.navigationToTourDetail(
                        navController,
                        tour
                    )
                }
            )
            LaunchedEffect(UserAnalytics.getTopProvince().size) {
                if (UserAnalytics.getTopProvince().isNotEmpty())
                    viewModel.updateItemSelected2(EProvince.valueOf(UserAnalytics.getTopProvince()[0].nameProvince.uppercase()))
            }

            Spacer(Modifier.height(30.dp))
            Text(
                stringResource(R.string.suggest1),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(Modifier.height(10.dp))
            ShowHorizontalListTour(
                UserAnalytics.lsMostPopularTour,
                onClickFavorite = { tour -> DataController.updateFavoriteTour(tour) },
                onClick = { tour ->
                    DataController.tourVM.navigationToTourDetail(
                        navController,
                        tour
                    )
                })
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                stringResource(R.string.suggest3),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(Modifier.height(10.dp))
            ShowHorizontalListTour(
                UserAnalytics.lsMostBookedTour,
                onClickFavorite = { tour -> DataController.updateFavoriteTour(tour) },
                onClick = { tour ->
                    DataController.tourVM.navigationToTourDetail(
                        navController,
                        tour
                    )
                })
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}