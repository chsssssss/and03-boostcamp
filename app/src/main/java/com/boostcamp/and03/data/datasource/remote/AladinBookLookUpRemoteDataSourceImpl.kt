package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.api.AladinBookLookUpApiService
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import javax.inject.Inject

class AladinBookLookUpRemoteDataSourceImpl @Inject constructor(
    private val aladinBookLookUpApiService: AladinBookLookUpApiService
): AladinBookLookUpRemoteDataSource {
    override suspend fun loadBookPage(
        itemId: String
    ): AladinBookLookUpResponse =
        aladinBookLookUpApiService.loadBookInfo(
            itemId = itemId
        )
}