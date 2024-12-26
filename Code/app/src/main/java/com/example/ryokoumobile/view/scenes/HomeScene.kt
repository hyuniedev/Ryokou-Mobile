package com.example.ryokoumobile.view.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ryokoumobile.ui.theme.RyokouMobileTheme
import com.example.ryokoumobile.view.components.FlashSaleTimeCountDown
import com.example.ryokoumobile.view.components.MyHorizontalPage
import com.example.ryokoumobile.view.components.MyTopBar
import com.example.ryokoumobile.view.components.RangeTimeScale

@Composable
fun HomeScene() {
    Scaffold(topBar = { MyTopBar() }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(vertical = 10.dp)) {
            MyHorizontalPage(listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSj0eiBXaznxdQOnmOYzhH67_T6HeNbGQMDwA&s",
                "https://static-oku.diqit.io/assets/worldwide/oku_ways_to_go_images4.jpg","abc","dcf","aaa"))
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                RangeTimeScale()
                FlashSaleTimeCountDown()

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