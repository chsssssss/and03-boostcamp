package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSource
import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookRemoteDataSourceModule {

    @Binds
    abstract fun bindBookStorageDataSource(
        bookStorageDataSourceImpl: BookStorageDataSourceImpl
    ): BookStorageDataSource
}
