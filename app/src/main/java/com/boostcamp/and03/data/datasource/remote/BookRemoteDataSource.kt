package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.model.request.BookEntity

interface BookRemoteDataSource {
    suspend fun saveBook(
        userId: String,
        book: BookEntity
    )

    suspend fun loadSavedBooks(
        userId: String
    ): List<BookEntity>
}