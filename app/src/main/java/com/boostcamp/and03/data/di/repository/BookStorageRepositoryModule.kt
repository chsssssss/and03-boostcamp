package com.boostcamp.and03.data.di.repository

import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookStorageRepositoryModule {

    @Binds
    abstract fun bindBookStorageRepository(
        bookStorageRepositoryImpl: BookStorageRepositoryImpl
    ): BookStorageRepository
}
