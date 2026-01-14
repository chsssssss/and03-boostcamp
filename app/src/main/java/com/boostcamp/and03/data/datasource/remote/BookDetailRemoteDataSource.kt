package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse

interface BookDetailRemoteDataSource {

    suspend fun loadBookPage(itemId: String): AladinBookLookUpResponse
}