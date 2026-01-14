package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.model.response.BookStorageResponse

interface BookStorageRepository {
    suspend fun getBooks(userId: String): List<BookStorageResponse>
}