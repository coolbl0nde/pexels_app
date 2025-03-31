package com.example.feature.presentation.details.presentation

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.R
import com.example.feature.presentation.details.presentation.component.BottomBarComponent
import com.example.feature.presentation.details.presentation.component.ImageZoomComponent
import com.example.feature.presentation.details.presentation.component.TopBarComponent

@Composable
fun DetailsScreen(
    photoId: Long,
    onBackClick: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TopBarComponent(
            photographer = uiState.value.getPhotographerOrDefault(),
            onBackClick = onBackClick,
        )

        when(val state = uiState.value) {

            is DetailsUiState.Loading -> {
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

            is DetailsUiState.Success -> {

                ImageZoomComponent(
                    imageUrl = state.photo.original,
                    width = state.photo.width,
                    height = state.photo.height,
                )

                BottomBarComponent(
                    onDownloadImage = {
                        viewModel.downloadImage(
                            context = context,
                            imageUrl = state.photo.original,
                            fileName = "pexels_${state.photo.id}"
                        )
                    },
                    onUpdateFavoriteStatus = {
                        viewModel.updateFavoriteStatus(
                            id = state.photo.id,
                            isFavorite = state.photo.isFavorite,
                        )
                    },
                    isFavorite = state.photo.isFavorite,
                )
            }

            is DetailsUiState.NotFoundError -> {

                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    Text(
                        text = stringResource(R.string.image_not_found),
                    )

                    TextButton(
                        onClick = onBackClick
                    ) {
                        Text(
                            text = stringResource(R.string.explore),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }

            else -> {

            }
        }
    }
}

