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
            perPage = response.per_page,
            totalResults = response.total_results,
            prevPage = response.prev_page,
            nextPage = response.next_page,
        )
    }
}