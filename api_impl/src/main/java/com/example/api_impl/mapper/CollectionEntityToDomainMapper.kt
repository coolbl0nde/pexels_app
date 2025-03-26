package com.example.api_impl.mapper

import com.example.core.model.FeaturedCollection
import com.example.data_api.entity.FeaturedCollectionEntity
import javax.inject.Inject

class CollectionEntityToDomainMapper @Inject constructor() {

    fun map(entity: FeaturedCollectionEntity): FeaturedCollection {
        return FeaturedCollection(
            id = entity.id,
            title = entity.title,
            timestamp = entity.timestamp,
        )
    }
}