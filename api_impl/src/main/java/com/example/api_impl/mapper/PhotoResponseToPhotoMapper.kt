package com.example.api_impl.mapper

import com.example.core.model.Photo
import com.example.data_api.model.PhotoResponse
import javax.inject.Inject

class PhotoResponseToPhotoMapper @Inject constructor() {

    fun map(response: PhotoResponse): Photo {
        return Photo(
            id = response.id,
            width = response.width,
            height = response.height,
            url = response.url,
            photographer = response.photographer,
            liked = response.liked,
            original = response.src.original,
            isFavorite = false,
        )
    }
}