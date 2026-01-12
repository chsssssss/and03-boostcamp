package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.search.naver.NaverBookSearchRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.search.naver.NaverBookSearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NaverBookSearchModule {

    @Binds
    @Singleton
    abstract fun bindNaverBookSearchRemoteDataSource(
        impl: NaverBookSearchRemoteDataSourceImpl
    ): NaverBookSearchRemoteDataSource
}