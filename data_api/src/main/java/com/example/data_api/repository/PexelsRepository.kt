package com.example.data_api.repository

import androidx.paging.PagingData
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import kotlinx.coroutines.flow.Flow

interface PexelsRepository {
    suspend fun getFeaturedCollections(page: Int, perPage: Int): List<FeaturedCollection>
    fun getSearchedPhotos(query: String, perPage: Int): Flow<PagingData<Photo>>
}