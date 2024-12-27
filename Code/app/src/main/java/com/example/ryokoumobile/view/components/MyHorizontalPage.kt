package com.example.ryokoumobile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

@Composable
fun MyHorizontalPage(lsLinkPicture: List<String>) {
    val statePager = rememberPagerState(initialPage = 0) {
        lsLinkPicture.size
    }

    LaunchedEffect(statePager) {
        while (true) {
            delay(5000)
            statePager.animateScrollToPage((statePager.currentPage + 1) % lsLinkPicture.size)
        }
    }

    HorizontalPager(
        state = statePager,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(0.27f),
    ) { index ->
        Image(
            painter = rememberAsyncImagePainter(lsLinkPicture[index]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        )
    }
}
