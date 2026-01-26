package com.boostcamp.and03.data.datasource.remote.book_detail

import com.boostcamp.and03.data.api.BookDetailApiService
import com.boostcamp.and03.data.model.response.BookLookUpResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BookDetailRemoteDataSourceImpl @Inject constructor(
    private val bookDetailApiService: BookDetailApiService,
    private val json: Json
): BookDetailRemoteDataSource {

    override suspend fun loadBookPage(itemId: String): BookLookUpResponse {
        val rawResponse = bookDetailApiService.loadBookInfo(itemId = itemId)

        val sanitizedResponse = rawResponse
            .trim()
            .removeSuffix(";")
            .replace("\\'", "'")

        return json.decodeFromString(sanitizedResponse)
    }
}