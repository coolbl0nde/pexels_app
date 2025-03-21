package com.example.api_impl.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.api_impl.bd.PexelsDatabase
import com.example.api_impl.mapper.SearchPhotoResponseToEntityMapper
import com.example.data_api.api.PexelsApi
import com.example.data_api.entity.PhotoEntity
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator(
    private val api: PexelsApi,
    private val query: String,
    private val database: PexelsDatabase,
    private val photoToEntityMapper: SearchPhotoResponseToEntityMapper,
): RemoteMediator<Int, PhotoEntity>() {

    private val photoDao = database.photoDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val lastUpdated = photoDao.getLastUpdated(query) ?: 0L

        return if (System.currentTimeMillis() - lastUpdated <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PhotoEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.pages.size + 1
            }

            val response = api.getSearchedPhotos(
                query = query,
                perPage = state.config.pageSize,
                page = page
            )
            val entities = photoToEntityMapper.map(response, query)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDao.deletePhotosByQuery(query)
                }
                photoDao.insertPhotos(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.photos.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}