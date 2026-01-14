package com.boostcamp.and03.data.datasource.remote.search.aladin

import com.boostcamp.and03.data.api.BookDetailApiService
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import javax.inject.Inject

class BookDetailRemoteDataSourceImpl @Inject constructor(
    private val bookDetailApiService: BookDetailApiService
): BookDetailRemoteDataSource {

    override suspend fun loadBookPage(itemId: String): AladinBookLookUpResponse =
        bookDetailApiService.loadBookInfo(itemId = itemId)
}