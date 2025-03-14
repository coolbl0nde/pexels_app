package com.example.data_api.model

import com.google.gson.annotations.SerializedName

data class FeaturedCollectionsResponse(
    @SerializedName("collections") val collections: List<CollectionItem>,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("prev_page")val prevPage: String?,
)

data class CollectionItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("private") val private: Boolean,
    @SerializedName("media_count") val mediaCount: Int,
    @SerializedName("photos_count") val photosCount: Int,
    @SerializedName("videos_count") val videosCount:Int,
)
