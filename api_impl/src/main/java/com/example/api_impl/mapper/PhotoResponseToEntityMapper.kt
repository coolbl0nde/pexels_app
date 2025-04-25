package com.example.api_impl.mapper

import com.example.data_api.entity.PhotoEntity
import com.example.data_api.model.PhotoResponse
import javax.inject.Inject

class PhotoResponseToEntityMapper @Inject constructor() {
    fun map(response: PhotoResponse, query: String): PhotoEntity {
        return PhotoEntity(
            id = response.id,
            width = response.width,
            height = response.height,
            url = response.url,
            photographer = response.photographer,
            liked = response.liked,
            original = response.src.original,
            query = query,
            isFavorite = false,
            favoriteMarkedAt = null,
        )
    }
}