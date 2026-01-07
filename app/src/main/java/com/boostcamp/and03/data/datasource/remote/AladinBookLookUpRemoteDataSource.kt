package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse

interface AladinBookLookUpRemoteDataSource {
    suspend fun loadBookPage(
        itemId: String
    ): AladinBookLookUpResponse
}