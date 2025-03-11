package com.example.data_api.repository

import com.example.core.model.FeaturedCollection

interface PexelsRepository {
    suspend fun getFeaturedCollections(page: Int, perPage: Int): List<FeaturedCollection>
}