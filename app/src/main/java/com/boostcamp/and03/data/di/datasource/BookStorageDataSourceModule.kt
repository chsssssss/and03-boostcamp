package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSource
import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSourceImpl
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSource
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSourceImpl
import com.boostcamp.and03.data.datasource.remote.quote.QuoteDataSource
import com.boostcamp.and03.data.datasource.remote.quote.QuoteDataSourceImpl
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

    @Binds
    abstract fun bindBookCharacterDataSource(
        characterDataSourceImpl: CharacterDataSourceImpl
    ): CharacterDataSource

    @Binds
    abstract fun bindBookQuoteDataSource(
        quoteDataSourceImpl: QuoteDataSourceImpl
    ): QuoteDataSource

}
