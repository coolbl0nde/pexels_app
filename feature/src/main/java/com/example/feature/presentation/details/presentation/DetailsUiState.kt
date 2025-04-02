package com.example.feature.presentation.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.core.model.Photo
import com.example.feature.R

sealed class DetailsUiState {
    object Loading: DetailsUiState()
    data class Success(val photo: Photo): DetailsUiState()
    data class Error(val message: String): DetailsUiState()
}

@Composable
fun DetailsUiState.getPhotographerOrDefault(): String {
    return if (this is DetailsUiState.Success) {
        photo.photographer
    } else {
        stringResource(R.string.loading)
    }
}