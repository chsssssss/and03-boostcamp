package com.boostcamp.and03.data.di

import com.boostcamp.and03.BuildConfig
import com.boostcamp.and03.data.api.NaverBookSearchService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.jvm.java

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverBookSearchRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverBookSearchOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NaverBookSearchNetworkModule {
    private const val BASE_URL = "https://openapi.naver.com"
    private const val CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
    private const val CLIENT_SECRET = BuildConfig.NAVER_CLIENT_SECRET

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    @NaverBookSearchOkHttpClient
    @Provides
    @Singleton
    fun provideNaverBookSearchOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()
                chain.proceed(newRequest)
            }.build()

    @NaverBookSearchRetrofit
    @Provides
    @Singleton
    fun provideNaverBookSearchRetrofit(
        json: Json,
        @NaverBookSearchOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    @Provides
    @Singleton
    fun provideNaverBookSearchService(
        @NaverBookSearchRetrofit retrofit: Retrofit
    ): NaverBookSearchService =
        retrofit.create(NaverBookSearchService::class.java)
}