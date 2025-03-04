package com.example.pexelsapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature.presentation.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
    }
}
