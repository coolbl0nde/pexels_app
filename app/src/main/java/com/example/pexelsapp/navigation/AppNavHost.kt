package com.example.pexelsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature.presentation.bookmarks.BookmarksScreen
import com.example.feature.presentation.details.presentation.DetailsScreen
import com.example.feature.presentation.home.presentation.screen.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ){
        composable<Home>{
            HomeScreen(
                onPhotoClick = {
                    navController.navigate(Details)
                }
            )
        }

        composable<Bookmarks> {
            BookmarksScreen()
        }

        composable<Details> {
            DetailsScreen()
        }
    }
}
