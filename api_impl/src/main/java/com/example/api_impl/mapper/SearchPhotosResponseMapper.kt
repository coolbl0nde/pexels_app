package com.example.api_impl.mapper

import com.example.core.model.SearchPhotos
import com.example.data_api.model.SearchPhotosResponse
import javax.inject.Inject

class SearchPhotosResponseMapper @Inject constructor(
    private val photoResponseMapper: PhotoResponseMapper,
) {

    fun map(response: SearchPhotosResponse): SearchPhotos {
        return SearchPhotos(
            photos = response.photos.map { photoResponse ->
                photoResponseMapper.map(photoResponse)
            },
            page = response.page,
            perPage = response.perPage,
            totalResults = response.totalResults,
            prevPage = response.prevPage,
            nextPage = response.nextPage,
        )
    }
}