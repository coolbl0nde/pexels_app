package com.example.api_impl.repository

import com.example.api_impl.mapper.FeaturedCollectionResponseMapper
import com.example.core.model.FeaturedCollection
import com.example.data_api.api.PexelsApi
import com.example.data_api.model.FeaturedCollectionsResponse
import com.example.data_api.repository.PexelsRepository
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val pexelsApi: PexelsApi,
    private val mapper: FeaturedCollectionResponseMapper,
): PexelsRepository {
    override suspend fun getFeaturedCollections(page: Int, perPage: Int): List<FeaturedCollection> {
        val response = pexelsApi.getFeaturedCollections(page, perPage)

        return mapper.map(response)
    }
}