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
import com.example.data_api.entity.RemoteKeys
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class FeaturedCollectionsRemoteMediator(
    private val api: PexelsApi,
    private val database: PexelsDatabase,
    private val collectionResponseToEntityMapper: CollectionResponseToEntityMapper
) : RemoteMediator<Int, FeaturedCollectionEntity>() {

    private val collectionDao = database.featuredCollectionDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val lastUpdated = collectionDao.getLastUpdated() ?: 0L

        return if (System.currentTimeMillis() - lastUpdated <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, FeaturedCollectionEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeysDao.remoteKeysById("featured_collection")
                    remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = api.getFeaturedCollections(
                perPage = state.config.pageSize,
                page = page
            )

            val entities = collectionResponseToEntityMapper.map(response, page)

            val endOfPaginationReached = response.collections.isEmpty()
            val nextPage = if (!endOfPaginationReached) page + 1 else null

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    collectionDao.deleteFeaturedCollections()
                    remoteKeysDao.deleteById("featured_collection")
                }

                collectionDao.insertCollections(entities)
                remoteKeysDao.insertRemoteKeys(
                    RemoteKeys(
                        id = "featured_collection",
                        nextKey = nextPage,
                        prevKey = if (page == 1) null else page - 1
                    )
                )
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("tag", "$e")
            MediatorResult.Error(e)
        }
    }
}
