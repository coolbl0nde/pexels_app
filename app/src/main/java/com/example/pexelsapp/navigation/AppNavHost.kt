package com.example.pexelsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature.presentation.bookmarks.BookmarksScreen
import com.example.feature.presentation.home.presentation.screen.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ){
        composable<Home>{
            HomeScreen(navController)
        }

        composable<Bookmarks> {
            BookmarksScreen()
        }
    }
}
