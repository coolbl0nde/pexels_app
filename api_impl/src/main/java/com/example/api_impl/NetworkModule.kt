package com.example.api_impl

import com.example.api_impl.repository.PexelsRepositoryImpl
import com.example.data_api.BuildConfig
import com.example.data_api.api.PexelsApi
import com.example.data_api.repository.PexelsRepository
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
    fun provideOkHttp(): OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.PEXELS_API_KEY)
                    .build()
                chain.proceed(request)
            }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePexelsApi(retrofit: Retrofit): PexelsApi{
        return retrofit.create(PexelsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePexelsRepository(pexelsApi: PexelsApi): PexelsRepository {
        return PexelsRepositoryImpl(pexelsApi = pexelsApi)
    }

}