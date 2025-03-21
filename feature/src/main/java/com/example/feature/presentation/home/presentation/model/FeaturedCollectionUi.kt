package com.example.feature.presentation.home.presentation.model

data class FeaturedCollectionUi(
    val id: String,
    val title: String,
    val timestamp: Long,
    val index: Int,
    var isSelected: Boolean = false,
)
