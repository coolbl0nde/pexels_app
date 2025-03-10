package com.example.data_api.model

data class FeaturedCollectionsResponse(
    val collections: List<CollectionItem>,
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val nextPage: String?,
    val prevPage: String?,

)

data class CollectionItem(
    val id: String,
    val title: String,
    val description: String,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount:Int,
)
