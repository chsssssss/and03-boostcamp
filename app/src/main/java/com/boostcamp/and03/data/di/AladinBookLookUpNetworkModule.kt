package com.boostcamp.and03.data.di

import com.boostcamp.and03.BuildConfig
import com.boostcamp.and03.data.api.AladinBookLookUpApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AladinBookLookUpNetworkModule {
    private const val BASE_URL = "http://www.aladin.co.kr"
    private const val TTB_KEY = BuildConfig.ALADIN_TTB_KEY

    private val contentType = "application/json".toMediaType()

    @AladinBookLookUpOkHttpClient
    @Provides
    @Singleton
    fun provideAladinBookLookUpOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("TTBKey", TTB_KEY)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }.build()

    @AladinBookLookUpRetrofit
    @Provides
    @Singleton
    fun provideAladinBookLookUpRetrofit(
        json: Json,
        @AladinBookLookUpOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    @Provides
    @Singleton
    fun provideAladinBookLookUpService(
        @AladinBookLookUpRetrofit retrofit: Retrofit
    ): AladinBookLookUpApiService =
        retrofit.create(AladinBookLookUpApiService::class.java)
}