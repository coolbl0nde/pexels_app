package com.example.api_impl

import com.example.api_impl.mapper.FeaturedCollectionResponseMapper
import com.example.api_impl.mapper.SearchPhotosResponseMapper
import com.example.api_impl.repository.PexelsRepositoryImpl
import com.example.core.utils.BASE_URL
import com.example.data_api.BuildConfig
import com.example.data_api.api.PexelsApi
import com.example.data_api.repository.PexelsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor() : Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", BuildConfig.PEXELS_API_KEY)
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttp(authInterceptor: Interceptor): OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providePexelsApi(retrofit: Retrofit): PexelsApi{
        return retrofit.create(PexelsApi::class.java)
    }


    @Provides
    @Singleton
    fun providePexelsRepository(
        pexelsApi: PexelsApi,
        featuredCollectionMapper: FeaturedCollectionResponseMapper,
        searchPhotosMapper: SearchPhotosResponseMapper,
    ): PexelsRepository {
        return PexelsRepositoryImpl(
            pexelsApi = pexelsApi,
            featuredCollectionMapper = featuredCollectionMapper,
            searchedPhotosMapper = searchPhotosMapper,
        )
    }

}