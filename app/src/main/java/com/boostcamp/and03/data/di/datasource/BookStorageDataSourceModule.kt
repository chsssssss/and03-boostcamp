package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.bookstorage.BookStorageDataSource
import com.boostcamp.and03.data.datasource.remote.bookstorage.BookStorageDataSourceImpl
import com.boostcamp.and03.data.datasource.remote.canvasmemo.CanvasMemoDataSource
import com.boostcamp.and03.data.datasource.remote.canvasmemo.CanvasMemoDataSourceImpl
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSource
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSourceImpl
import com.boostcamp.and03.data.datasource.remote.memo.MemoDataSource
import com.boostcamp.and03.data.datasource.remote.memo.MemoDataSourceImpl
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

    @Binds
    abstract fun bindBookMemoDataSource(
        memoDataSourceImpl: MemoDataSourceImpl
    ): MemoDataSource

    @Binds
    abstract fun bindBookCanvasMemoDataSource(
        canvasMemoDataSource: CanvasMemoDataSourceImpl
    ): CanvasMemoDataSource

}
