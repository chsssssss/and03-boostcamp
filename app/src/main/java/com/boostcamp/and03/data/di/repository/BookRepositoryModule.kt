package com.boostcamp.and03.data.di.repository

import com.boostcamp.and03.data.repository.booksearch.BookSearchRepository
import com.boostcamp.and03.data.repository.booksearch.BookSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        impl: BookSearchRepositoryImpl
    ): BookSearchRepository
}