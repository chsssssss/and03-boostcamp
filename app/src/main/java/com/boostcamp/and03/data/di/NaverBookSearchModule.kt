package com.boostcamp.and03.data.di

import com.boostcamp.and03.data.datasource.remote.BookSearchRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.NaverBookSearchRemoteDataSourceImpl
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
    ): BookSearchRemoteDataSource
}