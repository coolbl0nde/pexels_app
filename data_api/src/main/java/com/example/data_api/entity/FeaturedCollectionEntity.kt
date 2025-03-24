package com.example.data_api.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "featured_collections")
data class FeaturedCollectionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis(),
    val index: Int,
)
