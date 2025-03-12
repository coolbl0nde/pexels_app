package com.example.api_impl.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.api_impl.mapper.FeaturedCollectionResponseMapper
import com.example.api_impl.mapper.SearchPhotosResponseMapper
import com.example.api_impl.pagingSource.PhotosPagingSource
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.core.model.SearchPhotos
import com.example.data_api.api.PexelsApi
import com.example.data_api.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val pexelsApi: PexelsApi,
    private val featuredCollectionMapper: FeaturedCollectionResponseMapper,
    private val searchedPhotosMapper: SearchPhotosResponseMapper,
): PexelsRepository {
    override suspend fun getFeaturedCollections(page: Int, perPage: Int): List<FeaturedCollection> {
        val response = pexelsApi.getFeaturedCollections(page, perPage)

        return featuredCollectionMapper.map(response)
    }

    override fun getSearchedPhotos(query: String, perPage: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 5,
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