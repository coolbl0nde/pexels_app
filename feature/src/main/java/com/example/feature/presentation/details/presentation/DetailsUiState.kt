package com.example.feature.presentation.details.presentation

import com.example.core.model.Photo

sealed class DetailsUiState {
    object Loading: DetailsUiState()
    data class Success(val photo: Photo): DetailsUiState()
    data class Error(val message: String): DetailsUiState()
}

fun DetailsUiState.getPhotographerOrDefault(): String {
    return if (this is DetailsUiState.Success) {
        photo.photographer
    } else {
        "Loading..."
    }
}