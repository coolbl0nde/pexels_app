package com.example.pexelsapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature.presentation.bookmarks.presentation.BookmarksScreen
import com.example.core.utils.ANIMATION_DURATION_MS
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
            exitTransition = {
                if (targetState.destination.hierarchy.any {
                        it.hasRoute(route = Details::class)
                    } == true) {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                } else {
                    null
                }
            },
            enterTransition = {
                if (initialState.destination.hierarchy.any {
                        it.hasRoute(route = Details::class)
                    } == true) {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                } else {
                    null
                }
            }
        ){
            HomeScreen(
                onPhotoClick = { id ->
                    navController.navigate(Details(id))
                }
            )
        }

        composable<Bookmarks>(
            exitTransition = {
                if (targetState.destination.hierarchy.any {
                        it.hasRoute(route = Details::class)
                    } == true) {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(ANIMATION_DURATION_MS)
                    )
                } else {
                    null
                }
            },
            enterTransition = {
                if (initialState.destination.hierarchy.any {
                        it.hasRoute(route = Details::class)
                    } == true) {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(ANIMATION_DURATION_MS)
                    )
                } else {
                    null
                }
            }
        ) {
            BookmarksScreen(
                onPhotoClick = { id ->
                    navController.navigate(Details(id))
                },
                onExploreClick = {
                    navController.navigate(Home) {
                        popUpTo(Home) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Details>(
            enterTransition = { slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION_MS)
            ) },
            exitTransition = { slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION_MS)
            ) },
        ) { backStackEntry ->
            DetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
