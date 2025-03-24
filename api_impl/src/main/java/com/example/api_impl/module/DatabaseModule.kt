package com.example.api_impl.module

import android.content.Context
import androidx.room.Room
import com.example.api_impl.bd.PexelsDatabase
import com.example.data_api.dao.FeaturedCollectionDao
import com.example.data_api.dao.PhotoDao
import com.example.data_api.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PexelsDatabase{
        return Room.databaseBuilder(
            context,
            PexelsDatabase::class.java,
            "pexels_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(database: PexelsDatabase): PhotoDao{
        return database.photoDao()
    }

    @Provides
    @Singleton
    fun provideFeaturedCollection(database: PexelsDatabase): FeaturedCollectionDao{
        return database.featuredCollectionDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeys(database: PexelsDatabase): RemoteKeysDao{
        return database.remoteKeysDao()
    }
}