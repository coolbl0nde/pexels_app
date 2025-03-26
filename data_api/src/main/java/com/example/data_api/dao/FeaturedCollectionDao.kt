package com.example.data_api.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.model.FeaturedCollection
import com.example.data_api.entity.FeaturedCollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeaturedCollectionDao {

    @Query("SELECT * FROM featured_collections ORDER BY timestamp ASC")
    fun getFeaturedCollections(): PagingSource<Int, FeaturedCollectionEntity>

    @Query("SELECT COUNT(*) FROM featured_collections")
    suspend fun getFeaturedCollectionsCount(): Int

    @Query("SELECT MAX(timestamp) FROM featured_collections")
    suspend fun getLastUpdated(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: FeaturedCollectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollections(collection: List<FeaturedCollectionEntity>)

    @Delete
    suspend fun deleteFeaturedCollections(collection: List<FeaturedCollectionEntity>)
}