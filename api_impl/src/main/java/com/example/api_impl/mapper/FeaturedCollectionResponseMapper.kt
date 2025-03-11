package com.example.api_impl.mapper

import com.example.core.model.FeaturedCollection
import com.example.data_api.model.FeaturedCollectionsResponse
import javax.inject.Inject

class FeaturedCollectionResponseMapper @Inject constructor() {

    fun map(response: FeaturedCollectionsResponse): List<FeaturedCollection> {
        return response.collections.map { dto ->
            FeaturedCollection(
                id = dto.id,
                title = dto.title,
                mediaCount = dto.mediaCount,
                photosCount = dto.photosCount,
                videosCount = dto.videosCount,
            )
        }
    }

}