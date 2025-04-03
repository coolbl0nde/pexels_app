package com.example.data_api.api

import com.example.data_api.model.FeaturedCollectionsResponse
import com.example.data_api.model.PhotoResponse
import com.example.data_api.model.SearchPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {
    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int,
    ): FeaturedCollectionsResponse

    @GET("search")
    suspend fun getSearchedPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): SearchPhotosResponse

    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: Int,
    ): PhotoResponse
}