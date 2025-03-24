package com.example.data_api.model

import com.google.gson.annotations.SerializedName

data class SearchPhotosResponse(
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("photos") val photos: List<PhotoResponse>,
    @SerializedName("prev_page") val prevPage: String?,
    @SerializedName("next_page") val nextPage: String
)

data class PhotoResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("photographer") val photographer: String,
    @SerializedName("photographer_url") val photographerUrl: String,
    @SerializedName("photographer_id") val photographerId: Long,
    @SerializedName("avg_color") val avgColor: String,
    @SerializedName("src") val src: Src,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("alt") val alt: String
)

data class Src(
    @SerializedName("original") val original: String,
    @SerializedName("large2x") val large2x: String,
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
    @SerializedName("portrait") val portrait: String,
    @SerializedName("landscape") val landscape: String,
    @SerializedName("tiny") val tiny: String
)
