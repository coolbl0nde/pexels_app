package com.example.feature.presentation.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.feature.R
import com.example.feature.presentation.home.presentation.HomeViewModel
import com.example.feature.presentation.home.presentation.component.HorizontalListComponent
import com.example.feature.presentation.home.presentation.component.ImagesListComponent
import com.example.feature.presentation.home.presentation.component.SearchBarComponent
import kotlinx.collections.immutable.toPersistentList

@Composable
fun HomeScreen (
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
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

        val featuredCollections by viewModel.collections.collectAsState()
        val selectedItem by viewModel.selectedItem.collectAsState()

        val photos = viewModel.searchedPhotos.collectAsLazyPagingItems()

        SearchBarComponent(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                )
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = it },
            text = text,
            onTextChange = { text = it },
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (featuredCollections.isNotEmpty()) {
            HorizontalListComponent(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                ),
                featuredCollection = featuredCollections.toPersistentList(),
                selectedItem = selectedItem,
                onSelectedItemChange = { viewModel.selectItem(it) },
                updatePhotos = { viewModel.updatePhotos() },
            )

            Spacer(modifier = Modifier.height(14.dp))
        }


        when (photos.loadState.refresh) {
            is LoadState.Error -> {

            }
            is LoadState.Loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
            else -> {
                ImagesListComponent(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                    ),
                    photos = photos,
                )
            }
        }

        when (photos.loadState.append) {
            is LoadState.Error -> {
                //TODO
            }
            is LoadState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Pagination Loading")

                    CircularProgressIndicator(color = Color.Black)
                }
            }
            else -> {}
        }


        /*ImagesListComponent(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
            ),
            photos = photos,
        )*/
    }
}
