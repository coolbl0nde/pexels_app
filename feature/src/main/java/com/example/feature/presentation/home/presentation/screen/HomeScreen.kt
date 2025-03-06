package com.example.feature.presentation.home.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature.presentation.home.presentation.component.HorizontalListComponent
import com.example.feature.presentation.home.presentation.component.ImagesListComponent
import com.example.feature.presentation.home.presentation.component.SearchBarComponent

@Composable
fun HomeScreen (
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBarComponent(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(14.dp))

        HorizontalListComponent(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        ImagesListComponent(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
    }
}
