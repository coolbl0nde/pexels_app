package com.example.api_impl.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.api_impl.mapper.FeaturedCollectionResponseMapper
import com.example.api_impl.mapper.SearchPhotosResponseMapper
import com.example.api_impl.pagingSource.PhotosPagingSource
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.core.model.SearchPhotos
import com.example.core.utils.PREFETCH_DISTANCE
import com.example.data_api.api.PexelsApi
import com.example.data_api.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import okio.IOException
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val pexelsApi: PexelsApi,
    private val featuredCollectionMapper: FeaturedCollectionResponseMapper,
    private val searchedPhotosMapper: SearchPhotosResponseMapper,
): PexelsRepository {

    override suspend fun getFeaturedCollections(page: Int, perPage: Int): List<FeaturedCollection> {
        try {
            val response = pexelsApi.getFeaturedCollections(page, perPage)

            return featuredCollectionMapper.map(response)
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override fun getSearchedPhotos(query: String, perPage: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = perPage,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = perPage,
            ),
            pagingSourceFactory = {
                PhotosPagingSource(
                    pexelsApi = pexelsApi,
                    query = query,
                    searchPhotosResponseMapper = searchedPhotosMapper,
                )
            }
        ).flow
    }
}