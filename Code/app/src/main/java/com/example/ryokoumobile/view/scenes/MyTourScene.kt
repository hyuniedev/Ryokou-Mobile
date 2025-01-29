package com.example.ryokoumobile.view.scenes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.MyElevatedButton
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MyTourScene(modifier: Modifier = Modifier, navController: NavController) {
    val user = DataController.user.collectAsState()
    Box(modifier = modifier.padding(vertical = 15.dp)) {
        when (user.value) {
            null -> OnNotLoggedIn(navController)

            else -> OnLoggedIn()
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

@Composable
private fun OnLoggedIn() {
    val indexSelected = remember { MutableStateFlow(0) }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {
        SectionTour(
            title = "Tour đang diễn ra",
            index = 0,
            indexSelected = indexSelected.collectAsState().value,
            lsTour = listOf()
        ) { index -> indexSelected.value = index }
        SectionTour(
            title = "Tour sắp diễn ra",
            index = 1,
            indexSelected = indexSelected.collectAsState().value,
            lsTour = listOf()
        ) { index -> indexSelected.value = index }
        SectionTour(
            title = "Tour đã hoàn thành",
            index = 2,
            indexSelected = indexSelected.collectAsState().value,
            lsTour = listOf()
        ) { index -> indexSelected.value = index }
    }
}

@Composable
private fun SectionTour(
    title: String,
    index: Int,
    indexSelected: Int,
    lsTour: List<TourBooked>,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(horizontal = 10.dp),
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
        when (index) {
            0 -> {
                // TODO: Thêm ItemTourBooked và View cho Tour đang diễn ra
            }

            else -> {
                // TODO: Thực hiện hiển thị list các tourBooked
            }
        }
    }
}
