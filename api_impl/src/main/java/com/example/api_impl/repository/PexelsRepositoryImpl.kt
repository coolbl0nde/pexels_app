package com.example.api_impl.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.api_impl.bd.PexelsDatabase
import com.example.api_impl.mapper.CollectionEntityToDomainMapper
import com.example.api_impl.mapper.CollectionResponseToEntityMapper
import com.example.api_impl.mapper.PhotoEntityToPhotoMapper
import com.example.api_impl.mapper.SearchPhotoResponseToEntityMapper
import com.example.api_impl.remoteMediator.FeaturedCollectionsRemoteMediator
import com.example.api_impl.remoteMediator.PhotoRemoteMediator
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.core.utils.PREFETCH_DISTANCE_PHOTO
import com.example.data_api.api.PexelsApi
import com.example.data_api.dao.FeaturedCollectionDao
import com.example.data_api.dao.PhotoDao
import com.example.data_api.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PexelsRepositoryImpl @Inject constructor(
    private val pexelsApi: PexelsApi,
    private val photoDao: PhotoDao,
    private val featuredCollectionDao: FeaturedCollectionDao,
    private val database: PexelsDatabase,
    private val collectionResponseToEntityMapper: CollectionResponseToEntityMapper,
    private val collectionEntityToDomainMapper: CollectionEntityToDomainMapper,
    private val searchPhotoToEntityMapper: SearchPhotoResponseToEntityMapper,
    private val photoEntityToPhotoMapper: PhotoEntityToPhotoMapper,
): PexelsRepository {

    override fun getFeaturedCollections(perPage: Int): Flow<PagingData<FeaturedCollection>> {
        return Pager(
            config = PagingConfig(
                pageSize = perPage,
                initialLoadSize = perPage,
                prefetchDistance = PREFETCH_DISTANCE_PHOTO,
            ),
            remoteMediator = FeaturedCollectionsRemoteMediator(
                api = pexelsApi,
                database = database,
                collectionResponseToEntityMapper = collectionResponseToEntityMapper,
            ),
            pagingSourceFactory = { featuredCollectionDao.getFeaturedCollections() }
        ).flow.map { pagingData ->
            pagingData.map { collectionEntity ->
                collectionEntityToDomainMapper.map(collectionEntity)
            }
        }
    }

    override fun getSearchedPhotos(query: String, perPage: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = perPage,
                prefetchDistance = PREFETCH_DISTANCE_PHOTO,
                initialLoadSize = perPage,
            ),
            remoteMediator = PhotoRemoteMediator(
                query = query,
                api = pexelsApi,
                database = database,
                photoToEntityMapper = searchPhotoToEntityMapper,
            ),
            pagingSourceFactory = { photoDao.getPhotosByQuery(query) }
        ).flow.map { pagingData ->
            pagingData.map { photoEntity ->
                photoEntityToPhotoMapper.map(photoEntity)
            }
        }
    }

    override suspend fun getPhotoDetails(id: Long): Result<Photo> {
        return try {
            val photoEntity = photoDao.getPhoto(id)

            Result.success(photoEntityToPhotoMapper.map(photoEntity))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) {
        photoDao.updateFavoriteStatus(
            id = id,
            isFavorite = isFavorite,
        )
    }
}