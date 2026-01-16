package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.search.naver.BookSearchRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.search.naver.BookSearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookSearchDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookSearchRemoteDataSource(
        impl: BookSearchRemoteDataSourceImpl
    ): BookSearchRemoteDataSource
}