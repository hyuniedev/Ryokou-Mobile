package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.view.components.MyElevatedButton

@Composable
fun TourDetail(tour: Tour, navController: NavController) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            BodyTourDetail(tour, scrollState)
            BottomBarTourDetail(tour)
        }
        TopBarTourDetail(scrollState, navController)
    }
}

@Composable
private fun BodyTourDetail(tour: Tour, scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = rememberAsyncImagePainter(tour.lsFile[0]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
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
private fun TopBarTourDetail(scrollState: ScrollState, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(
                color = if (scrollState.value > 100) {
                    Color.LightGray.copy(alpha = 0.5f)
                } else Color.Transparent
            )
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