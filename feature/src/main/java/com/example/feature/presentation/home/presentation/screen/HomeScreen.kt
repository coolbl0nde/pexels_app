package com.example.feature.presentation.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.utils.empty
import com.example.feature.presentation.home.presentation.HomeViewModel
import com.example.feature.presentation.home.presentation.component.EmptyStateComponent
import com.example.feature.presentation.home.presentation.component.HorizontalListComponent
import com.example.feature.presentation.home.presentation.component.ImagesListComponent
import com.example.feature.presentation.home.presentation.component.NetworkStubComponent
import com.example.feature.presentation.home.presentation.component.SearchBarComponent
import kotlinx.collections.immutable.toPersistentList
import okio.IOException

@Composable
fun HomeScreen (
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        var text by remember {
            mutableStateOf("")
        }

        val featuredCollections by viewModel.collections.collectAsState()
        val selectedItem by viewModel.selectedItem.collectAsState()

        val photos = viewModel.searchedPhotos.collectAsLazyPagingItems()

        val focusManager = LocalFocusManager.current

        var isLoading by remember {
            mutableStateOf(false)
        }

        SearchBarComponent(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                )
                .fillMaxWidth(),
            text = text,
            onTextChange = { text = it },
            onSearch = {
                viewModel.onSearchRequest(it)
                viewModel.updatePhotos()
            },
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (featuredCollections.isNotEmpty()) {
            HorizontalListComponent(
                modifier = Modifier.padding( horizontal = 20.dp ),
                featuredCollection = featuredCollections.toPersistentList(),
                selectedItem = selectedItem,
                onSelectedItemChange = {
                    viewModel.selectItem(it)
                    focusManager.clearFocus()
                },
                updatePhotos = { viewModel.updatePhotos() },
                onTextChange = { text = it }
            )

            Spacer(modifier = Modifier.height(14.dp))
        }

        Box ( modifier = Modifier.weight(1f) ) {
            when (photos.loadState.refresh) {
                is LoadState.Error -> {
                    val error = (photos.loadState.refresh as LoadState.Error).error

                    if (error is NullPointerException) {
                        EmptyStateComponent(
                            onSearch = {
                                viewModel.selectItem(featuredCollections.first().title)
                                viewModel.updatePhotos()
                            },
                            onTextChange = { text = String.empty },
                        )
                    } else if (error is IOException){
                        NetworkStubComponent(
                            onTryAgain = { viewModel.retryFetchData() }
                        )
                    } else {
                        //TODO
                    }
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
                        modifier = Modifier.padding( horizontal = 20.dp ),
                        photos = photos,
                    )
                }
            }
        }



        when (photos.loadState.append) {
            is LoadState.Error -> {
                //TODO
            }
            is LoadState.Loading -> {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
            else -> { }
        }
    }
}
