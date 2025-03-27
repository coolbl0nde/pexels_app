package com.example.api_impl.mapper

import com.example.core.utils.AMOUNT_OF_PER_PAGE_FEATURED_COLLECTIONS
import com.example.data_api.entity.FeaturedCollectionEntity
import com.example.data_api.model.FeaturedCollectionsResponse
import javax.inject.Inject

class CollectionResponseToEntityMapper @Inject constructor() {

    fun map(response: FeaturedCollectionsResponse, page: Int): List<FeaturedCollectionEntity> {
        return response.collections.map { dto ->
            FeaturedCollectionEntity(
                id = dto.id,
                title = dto.title,
            )
        }
    }

}