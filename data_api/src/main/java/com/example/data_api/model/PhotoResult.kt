package com.example.data_api.model

import com.example.core.model.Photo

sealed class PhotoResult {
    data class Success(val photo: Photo): PhotoResult()
    sealed class Error: PhotoResult() {
        object NotFound: Error()
        data class Unknown(val message: String?): Error()
    }
}
