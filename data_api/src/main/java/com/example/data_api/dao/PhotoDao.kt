package com.example.data_api.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_api.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    fun getPhotos(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photos WHERE `query` = :query ORDER BY createdAt ASC")
    fun getPhotosByQuery(query: String): PagingSource<Int, PhotoEntity>

    @Query("SELECT * FROM photos WHERE isFavorite = 1 ORDER BY favoriteMarkedAt DESC")
    fun getBookmarks(): PagingSource<Int, PhotoEntity>
    /*ORDER BY createdAt ASC*/

    @Query("SELECT MAX(createdAt) FROM photos WHERE `query` = :query")
    suspend fun getLastUpdated(query: String): Long?

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getPhotosById(id: Long): PhotoEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotos(photoEntity: List<PhotoEntity>)

    @Query("UPDATE photos SET isFavorite = :isFavorite, favoriteMarkedAt = :favoriteMarkedAt WHERE id = :id")
    suspend fun updateFavoriteStatus(
        id: Long,
        isFavorite: Boolean,
        favoriteMarkedAt: Long?
    )

    @Query("DELETE FROM photos WHERE isFavorite = 0")
    suspend fun deletePhotos()

    @Delete
    suspend fun deleteAllPhotos(photos: List<PhotoEntity>)
}