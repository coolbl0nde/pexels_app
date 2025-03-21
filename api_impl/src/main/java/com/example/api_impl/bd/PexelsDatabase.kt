package com.example.api_impl.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_api.dao.FeaturedCollectionDao
import com.example.data_api.dao.PhotoDao
import com.example.data_api.dao.RemoteKeysDao
import com.example.data_api.entity.FeaturedCollectionEntity
import com.example.data_api.entity.PhotoEntity
import com.example.data_api.entity.RemoteKeys

@Database(
    entities = [
        PhotoEntity::class,
        FeaturedCollectionEntity::class,
        RemoteKeys::class,
    ],
    version = 7,
    exportSchema = false
)
abstract class PexelsDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun featuredCollectionDao(): FeaturedCollectionDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}