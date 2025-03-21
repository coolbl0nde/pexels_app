package com.example.data_api.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_api.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    fun getPhotos(): Flow<List<PhotoEntity>>

    @Query("SELECT COUNT(*) FROM photos WHERE `query` = :query")
    suspend fun getPhotosCountByQuery(query: String): Int

    @Query("SELECT * FROM photos WHERE `query` = :query ORDER BY createdAt ASC")
    fun getPhotosByQuery(query: String): PagingSource<Int, PhotoEntity>

    @Query("SELECT * FROM photos WHERE liked = 1 ORDER BY createdAt ASC")
    fun getLikedPhotos(): Flow<List<PhotoEntity>>

    @Query("SELECT MAX(createdAt) FROM photos WHERE `query` = :query")
    suspend fun getLastUpdated(query: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photoEntity: List<PhotoEntity>)

    @Query("UPDATE photos SET liked = 1 WHERE id = :photoId")
    suspend fun updateLikedStatus(photoId: Int)

    @Query("DELETE FROM photos WHERE id = :photoId")
    suspend fun deleteLikedPhoto(photoId: Int)

    @Query("DELETE FROM photos WHERE `query` = :query")
    suspend fun deletePhotosByQuery(query: String)

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()
}