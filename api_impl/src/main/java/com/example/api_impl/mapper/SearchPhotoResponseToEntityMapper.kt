package com.example.api_impl.mapper

import com.example.data_api.entity.PhotoEntity
import com.example.data_api.model.SearchPhotosResponse
import javax.inject.Inject

class SearchPhotoResponseToEntityMapper @Inject constructor(
    private val photoEntityMapper: PhotoResponseToEntityMapper,
) {
    fun map(response: SearchPhotosResponse, query: String): List<PhotoEntity> {
        return response.photos.map { photoEntityMapper.map(it, query) }
    }
}