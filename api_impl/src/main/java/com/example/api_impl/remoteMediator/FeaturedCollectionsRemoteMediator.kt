package com.example.api_impl.remoteMediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.api_impl.bd.PexelsDatabase
import com.example.api_impl.mapper.CollectionResponseToEntityMapper
import com.example.data_api.api.PexelsApi
import com.example.data_api.entity.FeaturedCollectionEntity
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class FeaturedCollectionsRemoteMediator(
    private val api: PexelsApi,
    private val database: PexelsDatabase,
    private val collectionResponseToEntityMapper: CollectionResponseToEntityMapper
) : RemoteMediator<Int, FeaturedCollectionEntity>() {

    private val collectionDao = database.featuredCollectionDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, FeaturedCollectionEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.pages.size + 1
            }

            val response = api.getFeaturedCollections(
                perPage = state.config.pageSize,
                page = page
            )
            val entities = collectionResponseToEntityMapper.map(response, page)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //val oneHourAgo = System.currentTimeMillis() - 1 * 60 * 1000

                    collectionDao.deleteFeaturedCollections()
                    //photoDao.deletePhotosByQuery(query)
                }
                collectionDao.insertCollections(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.collections.isEmpty())
        } catch (e: IOException) {
            val cachedCount = collectionDao.getFeaturedCollectionsCount()

            if (cachedCount > 0) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(e)
            }
        }catch (e: Exception) {
            Log.d("tag", "$e")
            MediatorResult.Error(e)
        }
    }
}
