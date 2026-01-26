package com.boostcamp.and03.data.datasource.remote.bookdetail

import com.boostcamp.and03.data.model.response.BookLookUpResponse

interface BookDetailRemoteDataSource {

    suspend fun loadBookPage(itemId: String): BookLookUpResponse
}