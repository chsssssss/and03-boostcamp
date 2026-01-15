package com.boostcamp.and03.data.datasource.remote.search.aladin

import com.boostcamp.and03.data.api.BookDetailApiService
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BookDetailRemoteDataSourceImpl @Inject constructor(
    private val bookDetailApiService: BookDetailApiService,
    private val json: Json
): BookDetailRemoteDataSource {

    override suspend fun loadBookPage(itemId: String): AladinBookLookUpResponse {
        val rawResponse = bookDetailApiService.loadBookInfo(itemId = itemId)

        val semicolonRemovedResponse = rawResponse.trim().removeSuffix(";")

        return json.decodeFromString(semicolonRemovedResponse)
    }
}