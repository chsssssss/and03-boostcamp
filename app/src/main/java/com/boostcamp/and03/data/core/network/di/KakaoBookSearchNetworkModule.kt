package com.boostcamp.and03.data.core.network.di

import com.boostcamp.and03.BuildConfig
import com.boostcamp.and03.data.feature.booklist.datasource.remote.KakaoBookSearchService
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
annotation class KakaoBookSearchRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoBookSearchOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object KakaoBookSearchNetworkModule {
    private const val BASE_URL = "https://dapi.kakao.com/"
    private const val REST_API_KEY = BuildConfig.KAKAO_REST_API_KEY

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    @KakaoBookSearchOkHttpClient
    @Provides
    @Singleton
    fun provideKakaoBookSearchOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK ${REST_API_KEY}")
                    .build()
                chain.proceed(newRequest)
            }.build()

    @KakaoBookSearchRetrofit
    @Provides
    @Singleton
    fun provideKakaoBookSearchRetrofit(
        json: Json,
        @KakaoBookSearchOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    @Provides
    @Singleton
    fun provideKakaoBookSearchService(
        @KakaoBookSearchRetrofit retrofit: Retrofit
    ): KakaoBookSearchService =
        retrofit.create(KakaoBookSearchService::class.java)
}