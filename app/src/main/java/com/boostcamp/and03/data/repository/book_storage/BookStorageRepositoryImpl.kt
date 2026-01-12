package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSource
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSource
import com.boostcamp.and03.data.datasource.remote.quote.QuoteDataSource
import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.QuoteResponse
import javax.inject.Inject

class BookStorageRepositoryImpl @Inject constructor(
    private val bookStorageDataSource: BookStorageDataSource,
    private val characterDataSource: CharacterDataSource,
    private val quoteDataSource: QuoteDataSource
): BookStorageRepository {
    override suspend fun getBooks(userId: String): List<BookStorageResponse> {
        return bookStorageDataSource.getBooks(userId)
    }

    override suspend fun getCharacters(userId: String, bookId: String): List<CharacterResponse> {
        return characterDataSource.getCharacters(userId, bookId)
    }

    override suspend fun addCharacter(userId: String, bookId: String, character: CharacterRequest) {
        characterDataSource.addCharacter(userId, bookId, character)
    }

    override suspend fun deleteCharacter(userId: String, bookId: String, characterId: String) {
        characterDataSource.deleteCharacter(userId, bookId, characterId)
    }

    override suspend fun getQuotes(userId: String, bookId: String): List<QuoteResponse> {
        return quoteDataSource.getQuotes(userId, bookId)
    }

    override suspend fun addQuote(userId: String, bookId: String, quote: QuoteRequest) {
        return quoteDataSource.addQuote(userId, bookId, quote)
    }
}