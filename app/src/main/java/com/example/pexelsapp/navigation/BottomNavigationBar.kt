package com.example.pexelsapp.navigation

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.utils.TAB_SELECTOR_ANIMATION_DURATION
import com.example.pexelsapp.R

data class TopLevelRoute<T : Any>(
    val route: T,
    val lightSelectedIconRes: Int,
    val darkSelectedIconRes: Int,
    val unselectedIconRes: Int,
)

val topLevelRoutes = listOf(
    TopLevelRoute(
        Home,
        R.drawable.light_filled_home_icon,
        R.drawable.dark_filled_home_icon,
        R.drawable.home_icon,
    ),
    TopLevelRoute(
        Bookmarks,
        R.drawable.light_filled_bookmark_icon,
        R.drawable.dark_filled_bookmark_icon,
        R.drawable.bookmark_icon,
    ),
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavigationBar (
        modifier = Modifier.height(64.dp),
    ) {
        topLevelRoutes.forEach { topLevelRoute ->

            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(route = topLevelRoute.route::class)
            } == true

            val indicatorWidth by animateDpAsState(
                targetValue = if (selected) 24.dp else 0.dp,
                animationSpec = tween(
                    durationMillis = TAB_SELECTOR_ANIMATION_DURATION,
                    easing = LinearOutSlowInEasing
                ),
            )

            NavigationBarItem(
                selected = selected,
                icon = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(indicatorWidth)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(50)
                                    ),
                            )
                        }

                        Icon(
                            modifier = Modifier.align(Alignment.Center),
                            painter =
                                if (selected) {
                                    if (isSystemInDarkTheme()) {
                                        painterResource(topLevelRoute.darkSelectedIconRes)
                                    } else {
                                        painterResource(topLevelRoute.lightSelectedIconRes)
                                    }
                                }
                                else painterResource(topLevelRoute.unselectedIconRes),
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                    }
                },
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )

        }
    }

}