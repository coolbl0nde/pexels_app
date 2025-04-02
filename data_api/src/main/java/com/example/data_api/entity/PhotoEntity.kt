package com.example.data_api.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val liked: Boolean,
    val original: String,
    val query: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean,
    val favoriteMarkedAt: Long?,
)
