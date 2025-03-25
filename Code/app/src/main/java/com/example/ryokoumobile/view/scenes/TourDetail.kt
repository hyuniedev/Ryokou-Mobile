package com.example.ryokoumobile.view.scenes

import android.text.Highlights
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.controller.UserAnalytics
import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.ToDoOnDay
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.model.uistate.TourDetailUiState
import com.example.ryokoumobile.view.components.BottomSheetShowTourSchedule
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyInputTextField
import com.example.ryokoumobile.view.components.MyLineTextHaveTextButton
import com.example.ryokoumobile.view.components.MyShowToast
import com.example.ryokoumobile.view.components.ShowHorizontalListTour
import com.example.ryokoumobile.viewmodel.TourDetailViewModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

@Composable
fun TourDetail(
    tour: Tour,
    navController: NavController,
    tourDetailVM: TourDetailViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    tourDetailVM.loadCompanyWithId(tour.company)
    tourDetailVM.loadRateOfTour(tour)

    val focus = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = null, indication = null) { focus.clearFocus() },
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BodyTourDetail(tour, scrollState, tourDetailVM, navController)
            BottomBarTourDetail(
                tour,
                user = DataController.user.collectAsState().value,
                tourDetailVM,
                navController
            )
        }
        TopBarTourDetail(navController)
    }
}

@Composable
private fun BodyTourDetail(
    tour: Tour,
    scrollState: ScrollState,
    tourDetailVM: TourDetailViewModel,
    navController: NavController
) {
    val user = DataController.user.collectAsState()
    val uiState = tourDetailVM.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ShowImageTour(tour)
        NameSection(tour, uiState.value.companyName)
        OverviewSection(tour)
        HorizontalDivider(thickness = 2.dp)
        TourSchedule(tour, tourDetailVM, uiState.value)
        HorizontalDivider(thickness = 2.dp)
        HighlightsSection(tour, uiState.value, tourDetailVM)
        HorizontalDivider(thickness = 2.dp)
        CommentOfTour(tour, uiState.value, tourDetailVM, user.value)
        HorizontalDivider(thickness = 2.dp)
        ServicesSection(tour)
        HorizontalDivider(thickness = 2.dp)
        SimilarToursSection(tour, navController = navController)
        Spacer(Modifier.height(100.dp))
    }
}

@Composable
fun SimilarToursSection(tour: Tour, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            stringResource(R.string.similarTours),
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(10.dp))
        ShowHorizontalListTour(
            lsTour = UserAnalytics.getSimilarTour(tour = tour),
            onClick = { tour ->
                DataController.tourVM.navigationToTourDetail(
                    tour = tour,
                    navController = navController
                )
            },
            onClickFavorite = { tour -> DataController.updateFavoriteTour(tour) }
        )
    }

}

@Composable
fun ServicesSection(tour: Tour) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(R.string.termsAndServices),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        )
        Text(text = tour.kisoku, modifier = Modifier.padding(start = 10.dp))
        Text(
            stringResource(R.string.moreInformation),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        )
        ButtonMoreInformation(R.string.moreInformation, { /*TODO("Show thong tin them")*/ })
        ButtonMoreInformation(
            R.string.contactServiceProvider,
            { /*TODO("Show cac phuong thuc lien he")*/ })
    }
}

@Composable
private fun ButtonMoreInformation(@StringRes title: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(title),
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            )
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun CommentOfTour(
    tour: Tour,
    uiState: TourDetailUiState,
    tourDetailVM: TourDetailViewModel,
    user: User?
) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            stringResource(R.string.rateAndReview),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(65.dp)
                )
                Text(
                    tour.getTotalRate().toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
            Spacer(Modifier.width(5.dp))
            Text(
                text = when (tour.getTotalRate()) {
                    in 4.5..5.0 -> stringResource(R.string.excellent)
                    in 3.5..4.5 -> stringResource(R.string.good)
                    in 2.5..3.5 -> stringResource(R.string.average)
                    in 1.5..2.5 -> stringResource(R.string.poor)
                    in 1.0..1.5 -> stringResource(R.string.veryPoor)
                    else -> stringResource(R.string.noReviewsYet)
                },
                style = TextStyle(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        uiState.lsRate.subList(
            0,
            if (uiState.lsRate.size <= 2) uiState.lsRate.size else uiState.numShowRate
        ).forEach { rate ->
            ItemComment(rate)
        }
        if (uiState.lsRate.isNotEmpty())
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        if (uiState.lsRate.size > 2 && uiState.numShowRate < uiState.lsRate.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { tourDetailVM.updateNumShowRate() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "More",
                    style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (user != null) {
            FieldUserComment(tour, uiState, tourDetailVM, user)
        }
    }
}

@Composable
private fun FieldUserComment(
    tour: Tour,
    uiState: TourDetailUiState,
    tourDetailVM: TourDetailViewModel,
    user: User?
) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(10.dp))
                repeat(5) { curRate ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { tourDetailVM.updateNumRateOfUser(curRate + 1) }
                            .padding(horizontal = 2.dp)) {
                        Icon(
                            if (curRate + 1 <= uiState.numRateOfUser)
                                painterResource(R.drawable.star)
                            else painterResource(
                                R.drawable.star_outline
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            MyInputTextField(
                title = "Your comment",
                value = uiState.userComment,
                isError = false,
                onValueChange = { tourDetailVM.updateUserComment(it) },
                trailingIcon = {
                    IconButton(onClick = {
                        tourDetailVM.sendComment(
                            user = user!!,
                            tour = tour,
                            context = context
                        )
                    }) {
                        Icon(
                            painterResource(R.drawable.send),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

        }
    }
}

@Composable
private fun ItemComment(rate: Rate) {
    var userName by remember { mutableStateOf("") }
    LaunchedEffect(rate.userId) {
        try {
            val doc = FirebaseController.firestore.collection("users")
                .document(rate.userId)
                .get()
                .await()
            userName = doc["fullName"].toString()
        } catch (e: Exception) {
            userName = rate.userId.substring(0, 5)
        }
    }
    HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
    Column {
        Row {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(5.dp))
            if (userName.isEmpty()) {
                CircularProgressIndicator()
            } else {
                Text(userName)
            }
        }
        Spacer(Modifier.height(3.dp))
        Row {
            repeat(rate.star) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(rate.comment, style = TextStyle(fontSize = 18.sp), maxLines = Int.MAX_VALUE)
    }
}

@Composable
private fun HighlightsSection(
    tour: Tour,
    uiState: TourDetailUiState,
    tourDetailVM: TourDetailViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .heightIn(min = 100.dp)
                .animateContentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                stringResource(R.string.highlight),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            )
            Text(
                tour.pointo,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(start = 10.dp),
                maxLines = if (uiState.isExtend) Int.MAX_VALUE else 6
            )
            if (uiState.isExtend) {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.9f),
                            Color.White
                        )
                    )
                )
                .clickable { tourDetailVM.updateExtend() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (uiState.isExtend) "Less" else "More",
                style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun TourSchedule(
    tour: Tour,
    tourDetailVM: TourDetailViewModel,
    uiState: TourDetailUiState
) {
    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
        Text(
            stringResource(R.string.tourSchedule),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        )
        MyLineTextHaveTextButton(
            text1 = "",
            textButton = stringResource(R.string.showDetailedSchedule),
            text2 = "",
            position = Arrangement.End
        ) {
            tourDetailVM.updateShowSchedule()
        }
    }
    if (uiState.isShowSchedule) {
        if (uiState.selectedDayOnSchedule.day.isEmpty())
            tourDetailVM.updateSelectedDayOnSchedule(tour.schedule[0])
        BottomSheetShowTourSchedule(
            onDismissRequest = { tourDetailVM.updateShowSchedule() },
            tour = tour,
            selectedDayOnSchedule = uiState.selectedDayOnSchedule,
            updateSelectedDayOnSchedule = { schedule ->
                tourDetailVM.updateSelectedDayOnSchedule(
                    schedule
                )
            }
        )
    }
}

@Composable
private fun OverviewSection(tour: Tour) {
    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(R.string.overview),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        )
        ARowInOverview(
            R.drawable.list_province,
            R.string.tinhThanhDiQua,
            tour.city.joinToString(separator = " - ")
        )
        ARowInOverview(
            R.drawable.ticket,
            R.string.soVeKhaDung,
            "${tour.ticketLimit.numLimitTicket - tour.ticketLimit.numCurrentTicket} vé"
        )
        ARowInOverview(R.drawable.time, R.string.durationTour, "${tour.durations} days")
        ARowInOverview(
            R.drawable.outline_tour,
            R.string.meetingPlace,
            tour.gatheringPlace
        )
    }
}

@Composable
private fun ARowInOverview(@DrawableRes icon: Int, @StringRes title: Int, detail: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(27.dp)
        )
        Column {
            Text(
                stringResource(title),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )
            Text(detail, style = TextStyle(fontSize = 18.sp))
        }
    }
}

@Composable
private fun RateOfTour(tour: Tour) {
    Row(
        modifier = Modifier.padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            "${tour.getTotalRate()}/5",
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                letterSpacing = TextUnit(value = 2f, type = TextUnitType.Sp)
            )
        )
        Text(
            stringResource(R.string.reviews, tour.lsRate.size),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
private fun NameSection(tour: Tour, companyName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(0.9f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                tour.name,
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
            )
            RateOfTour(tour)
            CompanyName(companyName)
        }
        IconButton(onClick = { DataController.updateFavoriteTour(tour) }) {
            Icon(
                imageVector = if (DataController.tourVM.getIsFavoriteTour(tour)) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .size(32.dp)
                    .weight(0.1f)
            )
        }
    }
}

@Composable
private fun CompanyName(companyName: String) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    shape = CircleShape
                )
                .padding(3.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.home_work),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = null,
                modifier = Modifier.size(23.dp)
            )
        }
        Text(
            companyName,
            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBarTourDetail(
    tour: Tour,
    user: User?,
    tourDetailVM: TourDetailViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val datePickerState = rememberDatePickerState()
    val uiState = tourDetailVM.uiState.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(topEnd = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.chuyenDiThuViTrongTamTay),
                style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 15.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(stringResource(R.string.giaTu), style = TextStyle(fontSize = 18.sp))
                Text(
                    "${tour.getPriceWithFormatted()}đ",
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            MyElevatedButton(
                title = if (tour.ticketLimit.numLimitTicket - tour.ticketLimit.numCurrentTicket == 0) "Hết vé" else "Đặt vé",
                isEnable = if (tour.ticketLimit.numLimitTicket - tour.ticketLimit.numCurrentTicket == 0) false else true
            ) {
                if (user == null) {
                    navController.navigate(Scenes.AuthGroup.Login.route)
                } else {
                    tourDetailVM.updateIsSelectTour()
                }
            }
        }
    }
    if (uiState.value.isSelectTour) {
        ModalBottomSheet(
            onDismissRequest = { tourDetailVM.updateIsSelectTour() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Chọn ngày bắt đầu đi: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    IconButton(onClick = { tourDetailVM.updateIsShowDatePicker() }) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                if (uiState.value.dateSelected != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(horizontal = 15.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Ngày đi: ",
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                tourDetailVM.formatDate(uiState.value.dateSelected!!),
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Ngày về: ",
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                tourDetailVM.formatDate(tourDetailVM.getEndDay(tour.durations)),
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Số lượng vé: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            tourDetailVM.updateNumTicket(
                                context,
                                uiState.value.numTicket - 1,
                                tour.ticketLimit.numLimitTicket - tour.ticketLimit.numCurrentTicket
                            )
                        }) {
                            Text(
                                "-",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = if (uiState.value.numTicket == 1) Color.DarkGray.copy(
                                        alpha = 0.5f
                                    ) else Color.Black
                                )
                            )
                        }
                        Box {
                            Text(
                                uiState.value.numTicket.toString(),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                            )
                        }
                        IconButton(onClick = {
                            tourDetailVM.updateNumTicket(
                                context,
                                uiState.value.numTicket + 1,
                                tour.ticketLimit.numLimitTicket - tour.ticketLimit.numCurrentTicket
                            )
                        }) {
                            Text(
                                "+",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = if (tour.ticketLimit.numCurrentTicket + uiState.value.numTicket == tour.ticketLimit.numLimitTicket) Color.DarkGray.copy(
                                        alpha = 0.5f
                                    ) else Color.Black
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Giá:", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                    val tmpTourBooked =
                        TourBooked(numPerson = uiState.value.numTicket, tourId = tour.id)
                    Text(
                        "${tmpTourBooked.getTotalPay()}đ",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MyElevatedButton(title = "Thanh toán", modifier = Modifier.fillMaxWidth(0.5f)) {
                        tourDetailVM.thanhToan(context, tour, navController)
                    }
                }
            }
        }
    }
    if (uiState.value.isShowDatePicker) {
        DatePickerDialog(
            onDismissRequest = { tourDetailVM.updateIsShowDatePicker() },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { milis ->
                        val selectTime = Timestamp(Date(milis))
                        if (selectTime.toDate().after(Calendar.getInstance().time)) {
                            tourDetailVM.updateSelectedDate(Timestamp(Date(milis)))
                            tourDetailVM.updateIsShowDatePicker()
                        } else {
                            MyShowToast(context, "Ngày chọn không hợp lệ!")
                        }
                    }
                }) {
                    Text(
                        "Chọn",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { tourDetailVM.updateIsShowDatePicker() }) {
                    Text(
                        "Hủy",
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun TopBarTourDetail(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color.DarkGray.copy(alpha = 0.2f))
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun ShowImageTour(tour: Tour) {
    Image(
        painter = rememberAsyncImagePainter(tour.lsFile[0]),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}