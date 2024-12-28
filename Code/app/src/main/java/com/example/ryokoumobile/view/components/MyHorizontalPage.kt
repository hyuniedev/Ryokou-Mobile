package com.example.ryokoumobile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
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
            .height(200.dp),
    ) { index ->
        Box(contentAlignment = Alignment.BottomCenter) {
            Image(
                painter = rememberAsyncImagePainter(lsLinkPicture[index]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
            Row(
                Modifier.padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(lsLinkPicture.size) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(50.dp)
                            )
                            .clip(RoundedCornerShape(50.dp))
                            .background(color = if (index == it) MaterialTheme.colorScheme.primary else Color.Transparent),
                    )
                }
            }
        }
    }
}
