package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.boostcamp.and03.data.model.response.CharacterResponse

interface BookStorageRepository {
    suspend fun getBooks(userId: String): List<BookStorageResponse>

    suspend fun getCharacters(userId: String, bookId: String): List<CharacterResponse>

}