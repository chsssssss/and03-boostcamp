package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.bookdetail.BookDetailRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.bookdetail.BookDetailRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookDetailDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookDetailRemoteDataSource(
        impl: BookDetailRemoteDataSourceImpl
    ): BookDetailRemoteDataSource
}