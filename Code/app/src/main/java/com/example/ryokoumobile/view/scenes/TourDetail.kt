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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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
import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.Schedule
import com.example.ryokoumobile.model.entity.ToDoOnDay
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.uistate.TourDetailUiState
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyLineTextHaveTextButton
import com.example.ryokoumobile.viewmodel.TourDetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun TourDetail(
    tour: Tour,
    navController: NavController,
    tourDetailVM: TourDetailViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    tourDetailVM.loadCompanyWithId(tour.company)
    tourDetailVM.loadRateOfTour(tour)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BodyTourDetail(tour, scrollState, tourDetailVM)
            BottomBarTourDetail(tour)
        }
        TopBarTourDetail(navController)
    }
}

@Composable
private fun BodyTourDetail(
    tour: Tour,
    scrollState: ScrollState,
    tourDetailVM: TourDetailViewModel
) {
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
        CommentOfTour(tour, tourDetailVM)
        HorizontalDivider(thickness = 2.dp)
        ServicesSection(tour)
        // TODO("Them goi y tour tuong tu")
        Spacer(Modifier.height(100.dp))
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
                shape = RoundedCornerShape(15.dp)
            )
            .clip(
                RoundedCornerShape(15.dp)
            )
            .clickable { }
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
private fun CommentOfTour(tour: Tour, tourDetailVM: TourDetailViewModel) {
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
        tour.lsRate.forEach {
            ItemComment(it, tourDetailVM)
        }
//        TODO("Them khu vuc de nhap danh gia cho nguoi dung")
    }
}

@Composable
private fun ItemComment(rate: Rate, tourDetailVM: TourDetailViewModel) {
    val name = tourDetailVM.getUserNameWithUID(rate.userId).collectAsState()
    Column {
        Row {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(name.value)
        }
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
        Text(rate.comment, style = TextStyle(fontSize = 18.sp), maxLines = Int.MAX_VALUE)
    }
}

@Composable
private fun HighlightsSection(
    tour: Tour,
    uiState: TourDetailUiState,
    tourDetailVM: TourDetailViewModel
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .animateContentSize(),
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

@OptIn(ExperimentalMaterial3Api::class)
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
            "",
            stringResource(R.string.showDetailedSchedule),
            "",
            position = Arrangement.End
        ) {
            tourDetailVM.updateShowSchedule()
        }
    }
    if (uiState.isShowSchedule) {
        if (uiState.selectedDayOnSchedule.day.isEmpty())
            tourDetailVM.updateSelectedDayOnSchedule(tour.schedule[0])
        ModalBottomSheet(
            onDismissRequest = { tourDetailVM.updateShowSchedule() },
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    LazyColumn {
                        items(tour.schedule) { schedule ->
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
                                    .background(if (uiState.selectedDayOnSchedule == schedule) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                                    .clickable { tourDetailVM.updateSelectedDayOnSchedule(schedule) }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    schedule.day,
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            .padding(5.dp)
                    ) {
                        LazyColumn {
                            items(uiState.selectedDayOnSchedule.lsTodo) { toDo ->
                                LineToDo(toDo)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LineToDo(toDo: ToDoOnDay) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        Text("${toDo.hour}:${toDo.minute}")
        Spacer(Modifier.width(5.dp))
        Text(toDo.content, maxLines = Int.MAX_VALUE)
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

@Composable
private fun BottomBarTourDetail(tour: Tour) {
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
                    tour.getPriceWithFormatted(),
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            MyElevatedButton(title = "Select") { }
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