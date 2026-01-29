package com.boostcamp.and03.data.di.repository

import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepositoryImpl
import com.boostcamp.and03.data.repository.canvasmemo.CanvasMemoRepositoryImpl
import com.boostcamp.and03.domain.repository.CanvasMemoRepository
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

    @Binds
    abstract fun bindCanvasMemoRepository(
        canvasMemoRepositoryImpl: CanvasMemoRepositoryImpl
    ): CanvasMemoRepository
}
