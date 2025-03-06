package com.example.feature.presentation.home.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature.R
import com.example.feature.presentation.home.presentation.component.HorizontalListComponent
import com.example.feature.presentation.home.presentation.component.ImagesListComponent
import com.example.feature.presentation.home.presentation.component.SearchBarComponent

@Composable
fun HomeScreen (
    navController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        var expanded by remember {
            mutableStateOf(false)
        }
        var text by remember {
            mutableStateOf("")
        }

        val featuredCollection = listOf("Ice", "Watches", "Drawing", "Dog", "Cat")
        var selectedItem by remember {
            mutableStateOf(featuredCollection.first())
        }

        val imageList = listOf(
            R.drawable.picture_1,
            R.drawable.picture_2,
            R.drawable.picture_3,
            R.drawable.picture_4,
            R.drawable.picture_5,
            R.drawable.picture_6,
        )

        SearchBarComponent(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                )
                .align(Alignment.CenterHorizontally),
            expanded = expanded,
            onExpandedChange = { expanded = it },
            text = text,
            onTextChange = { text = it },
        )

        Spacer(modifier = Modifier.height(14.dp))

        HorizontalListComponent(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
            ),
            featuredCollection = featuredCollection,
            selectedItem = selectedItem,
            onSelectedItemChange = { selectedItem = it },
        )

        Spacer(modifier = Modifier.height(14.dp))

        ImagesListComponent(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
            ),
            imageList = imageList,
        )
    }
}
