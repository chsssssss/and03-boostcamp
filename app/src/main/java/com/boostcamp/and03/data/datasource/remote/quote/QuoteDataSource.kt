package com.boostcamp.and03.data.datasource.remote.quote

import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.response.QuoteResponse

interface QuoteDataSource {
    suspend fun getQuotes(userId: String, bookId: String): List<QuoteResponse>

    suspend fun addQuote(userId: String, bookId: String, quote: QuoteRequest)

    suspend fun deleteQuote(userId: String, bookId: String, quoteId: String)
}