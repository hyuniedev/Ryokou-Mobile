package com.example.ryokoumobile.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.ui.theme.IndicatorColor

@Composable
fun MyNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Scenes.MainGroup.MyTour.apply {
            this.icon = ImageVector.vectorResource(R.drawable.outline_tour)
            this.iconOnSelected = ImageVector.vectorResource(R.drawable.filled_tour)
        }
        
        Scenes.MainGroup.getScenes().forEach { scene ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (currentRoute == scene.route) scene.iconOnSelected else scene.icon,
                        contentDescription = scene.route,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                label = {
                    if (currentRoute == scene.route) {
                        Text(
                            scene.route,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors()
                    .copy(selectedIndicatorColor = IndicatorColor),
                onClick = {
                    navController.navigate(scene.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                selected = currentRoute == scene.route
            )
        }
    }
}