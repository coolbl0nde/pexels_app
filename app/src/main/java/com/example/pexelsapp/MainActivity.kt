package com.example.pexelsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.utils.SPLASH_SCREEN_DELAY_MS
import com.example.feature.presentation.home.presentation.HomeViewModel
import com.example.pexelsapp.navigation.AppNavHost
import com.example.pexelsapp.navigation.BottomNavigationBar
import com.example.pexelsapp.navigation.Details
import com.example.pexelsapp.ui.theme.PexelsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            withTimeoutOrNull(SPLASH_SCREEN_DELAY_MS) {
                homeViewModel.isLoading
                    .collect { isLoading ->
                        if (!isLoading) {
                            cancel()
                        }
                    }
            }
            if (homeViewModel.isLoading.value) {
                homeViewModel.setIsLoading(false)
            }
        }

        splashscreen.setKeepOnScreenCondition {
            homeViewModel.isLoading.value
        }

        setContent {
            PexelsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    val navController = rememberNavController()

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    Scaffold (
                        bottomBar = {

                            if (currentDestination?.hierarchy?.any {
                                it.hasRoute(route = Details::class)
                            } == false) {
                                BottomNavigationBar(navController)
                            }
                        }
                    ) { paddingValues ->
                        AppNavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                            setIsLoading = { homeViewModel.setIsLoading(false) },
                        )
                    }

                }
            }
        }
    }
}