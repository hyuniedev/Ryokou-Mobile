package com.example.ryokoumobile.view.scenes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyLineTextHaveTextButton
import com.example.ryokoumobile.view.components.RecommendedTours
import com.example.ryokoumobile.view.components.ShowGridTour
import com.example.ryokoumobile.view.components.SubModalBottomSheetShowTourSchedule
import com.example.ryokoumobile.view.items.ItemBookedTour
import com.example.ryokoumobile.viewmodel.MyTourViewModel

@Composable
fun MyTourScene(
    modifier: Modifier = Modifier,
    myTourVM: MyTourViewModel = viewModel(),
    navController: NavController
) {
    val user = DataController.user.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (user.value) {
            null -> OnNotLoggedIn(navController)

            else -> OnLoggedIn(myTourVM, navController)
        }
    }
}

@Composable
private fun OnNotLoggedIn(navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.LightGray.copy(alpha = 0.15f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyElevatedButton(
                Modifier
                    .weight(0.3f)
                    .padding(horizontal = 20.dp),
                "Login",
                isFilled = false
            ) {
                navController.navigate(
                    Scenes.AuthGroup.Login.route
                )
            }
            MyElevatedButton(
                Modifier
                    .weight(0.3f)
                    .padding(
                        horizontal = 20.dp
                    ),
                "Sign in"
            ) { navController.navigate(Scenes.AuthGroup.SignIn.route) }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.bill_cross_broken),
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )
            Spacer(Modifier.height(15.dp))
            Text(
                stringResource(R.string.myTourNotLoggedIn),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnLoggedIn(myTourVM: MyTourViewModel, navController: NavController) {
    val scrollState = rememberScrollState()
    val uiState = myTourVM.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        SectionTour(
            title = "Tour đang diễn ra",
            index = 0,
            indexSelected = uiState.value.indexSelected,
            myTourVM = myTourVM,
            lsTour = uiState.value.lsGoingTour
        ) { index -> myTourVM.updateIndexSelected(index) }
        SectionTour(
            title = "Tour sắp diễn ra",
            index = 1,
            indexSelected = uiState.value.indexSelected,
            myTourVM = myTourVM,
            lsTour = uiState.value.lsWaitTour
        ) { index -> myTourVM.updateIndexSelected(index) }
        SectionTour(
            title = "Tour đã hoàn thành",
            index = 2,
            indexSelected = uiState.value.indexSelected,
            myTourVM = myTourVM,
            lsTour = uiState.value.lsGoneTour
        ) { index -> myTourVM.updateIndexSelected(index) }

        // Recommend Tours Section
        val lsRecommendTour = DataController.tourVM.uiState.collectAsState()
        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
            RecommendedTours(lsRecommendTour.value, navController)
        }
    }
    if (uiState.value.bookedTourFocus != null) {
        val tour = DataController.tourVM.getTourFromID(uiState.value.bookedTourFocus!!.tourId)
        if (uiState.value.selectedDayOnSchedule.day.isEmpty())
            myTourVM.updateSelectedDayOnSchedule(tour.schedule[0])
        ModalBottomSheet(onDismissRequest = { myTourVM.unfocusBookedTour() }) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MyElevatedButton(
                        title = "Lịch trình",
                        isFilled = uiState.value.isScheduleShow
                    ) {
                        myTourVM.updateIsScheduleShow(true)
                    }
                    Spacer(Modifier.width(20.dp))
                    MyElevatedButton(
                        title = "Thông tin vé",
                        isFilled = !uiState.value.isScheduleShow
                    ) {
                        myTourVM.updateIsScheduleShow(false)
                    }
                }
                if (uiState.value.isScheduleShow) {
                    SubModalBottomSheetShowTourSchedule(
                        tour = tour,
                        selectedDayOnSchedule = uiState.value.selectedDayOnSchedule,
                        showIcon = false,
                        updateSelectedDayOnSchedule = { schedule ->
                            myTourVM.updateSelectedDayOnSchedule(schedule)
                        })
                    MyLineTextHaveTextButton(
                        modifier = Modifier.padding(end = 15.dp, bottom = 10.dp),
                        "",
                        "Xem chi tiết tour",
                        "",
                        position = Arrangement.End
                    ) {
                        DataController.tourVM.navigationToTourDetail(navController, tour)
                    }
                } else {
                    ShowInfoTicket(uiState.value.bookedTourFocus!!)
                }
            }
        }
    }
}

@Composable
private fun ShowInfoTicket(bookedTour: TourBooked) {
    val tour = DataController.tourVM.getTourFromID(bookedTour.tourId)
    Column(
        modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RowInfoTicket("Người đặt vé: ", DataController.user.collectAsState().value!!.fullName)
        RowInfoTicket("Số lượng: ", "${bookedTour.numPerson} vé")
        RowInfoTicket("Địa điểm tập hợp: ", tour.gatheringPlace)
        RowInfoTicket("Ngày đi: ", bookedTour.formatDate(bookedTour.startDay.toDate()))
        RowInfoTicket("Ngày về: ", bookedTour.formatDate(bookedTour.getEndDay()))
        RowInfoTicket("Tổng tiền: ", "${bookedTour.getTotalPay()}đ")
    }
}

@Composable
private fun RowInfoTicket(title: String, info: String) {
    Row {
        Text(title, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        Text(info, style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 20.sp))
    }
}


@Composable
private fun SectionTour(
    title: String,
    index: Int,
    indexSelected: Int,
    lsTour: List<TourBooked>,
    myTourVM: MyTourViewModel,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors()
            .copy(containerColor = Color.LightGray.copy(alpha = 0.2f)),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        onClick = { onClick(index) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                style = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Icon(
                if (index == indexSelected) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(31.dp)
            )
        }
        if (index == indexSelected) {
            HorizontalDivider(color = MaterialTheme.colorScheme.primary)
            if (lsTour.isNotEmpty()) {
                when (index) {
                    0 -> {
                        Box(modifier = Modifier.padding(15.dp)) {
                            ItemBookedTour(bookedTour = lsTour[0]) { myTourVM.onClick(lsTour[0]) }
                        }
                    }

                    else -> {
                        ShowGridTour(lsTour) { tour -> myTourVM.onClick(tour) }
                    }
                }
            } else {
                Text(
                    "Danh sách rỗng.",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
