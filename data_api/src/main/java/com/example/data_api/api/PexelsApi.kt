package com.example.data_api.api

import com.example.data_api.model.FeaturedCollectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {
    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int,
    ): FeaturedCollectionsResponse
}