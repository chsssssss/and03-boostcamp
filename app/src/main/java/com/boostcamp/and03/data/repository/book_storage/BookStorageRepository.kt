package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.QuoteResponse

interface BookStorageRepository {
    suspend fun getBooks(userId: String): List<BookStorageResponse>

    suspend fun getCharacters(userId: String, bookId: String): List<CharacterResponse>

    suspend fun addCharacter(userId: String, bookId: String, character: CharacterRequest)

    suspend fun getQuotes(userId: String, bookId: String): List<QuoteResponse>

    suspend fun addQuote(userId: String, bookId: String, quote: QuoteRequest)

}