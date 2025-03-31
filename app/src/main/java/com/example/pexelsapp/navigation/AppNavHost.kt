package com.example.pexelsapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
        startDestination = Home,
    ){
        composable<Home>(
            exitTransition = { slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            ) },
            enterTransition = { slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(600)
            ) },
        ){
            HomeScreen(
                onPhotoClick = { id ->
                    navController.navigate(Details(id))
                }
            )
        }

        composable<Bookmarks> {
            BookmarksScreen()
        }

        composable<Details>(
            enterTransition = { slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            ) },
            exitTransition = { slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(600)
            ) },
        ) { backStackEntry ->
            val details: Details = backStackEntry.toRoute()
            DetailsScreen(
                photoId = details.id,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
