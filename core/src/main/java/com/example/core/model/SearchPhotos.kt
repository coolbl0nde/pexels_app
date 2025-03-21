package com.example.core.model

data class SearchPhotos(
    val photos: List<Photo>,
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val prevPage: String?,
    val nextPage: String,
)

data class Photo(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val liked: Boolean,
    val original: String,
)
