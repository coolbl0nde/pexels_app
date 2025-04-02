package com.example.data_api.repository

import androidx.paging.PagingData
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import kotlinx.coroutines.flow.Flow

interface PexelsRepository {
    fun getFeaturedCollections(perPage: Int): Flow<PagingData<FeaturedCollection>>
    fun getSearchedPhotos(query: String, perPage: Int): Flow<PagingData<Photo>>
    suspend fun getPhotoDetails(id: Long): Result<Photo>
    suspend fun updateFavoriteStatus(photoId: Long, isFavorite: Boolean)
}