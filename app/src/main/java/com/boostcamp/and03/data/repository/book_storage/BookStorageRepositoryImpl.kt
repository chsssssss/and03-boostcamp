package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSource
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSource
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.boostcamp.and03.data.model.response.CharacterResponse
import javax.inject.Inject

class BookStorageRepositoryImpl @Inject constructor(
    private val bookStorageDataSource: BookStorageDataSource,
    private val characterDataSource: CharacterDataSource
): BookStorageRepository {
    override suspend fun getBooks(userId: String): List<BookStorageResponse> {
        return bookStorageDataSource.getBooks(userId)
    }

    override suspend fun getCharacters(userId: String, bookId: String): List<CharacterResponse> {
        return characterDataSource.getCharacters(userId, bookId)
    }
}