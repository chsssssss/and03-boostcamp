package com.boostcamp.and03.data.feature.booklist.di

import com.boostcamp.and03.data.feature.booklist.datasource.remote.KakaoBookSearchRemoteDataSource
import com.boostcamp.and03.data.feature.booklist.datasource.remote.KakaoBookSearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoBookSearchModule {

    @Binds
    @Singleton
    abstract fun bindKakaoBookSearchRemoteDataSource(
        impl: KakaoBookSearchRemoteDataSourceImpl
    ): KakaoBookSearchRemoteDataSource
}