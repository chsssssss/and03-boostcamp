package com.boostcamp.and03.data.di.network

import com.boostcamp.and03.BuildConfig
import com.boostcamp.and03.data.api.BookDetailApiService
import com.boostcamp.and03.data.di.qualifier.BookDetailOkHttpClient
import com.boostcamp.and03.data.di.qualifier.BookDetailRetrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookDetailNetworkModule {
    private const val BASE_URL = "http://www.aladin.co.kr"
    private const val TTB_KEY = BuildConfig.ALADIN_TTB_KEY

    private val contentType = "application/json".toMediaType()

    @BookDetailOkHttpClient
    @Provides
    @Singleton
    fun provideBookDetailOkHttpClient(): OkHttpClient =
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

    @BookDetailRetrofit
    @Provides
    @Singleton
    fun provideBookDetailRetrofit(
        @BookDetailOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create()) // String으로 변환
            .build()

    @Provides
    @Singleton
    fun provideBookDetailService(
        @BookDetailRetrofit retrofit: Retrofit
    ): BookDetailApiService =
        retrofit.create(BookDetailApiService::class.java)
}