package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.api.AladinBookLookUpApiService
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AladinBookLookUpRemoteDataSourceImpl @Inject constructor(
    private val aladinBookLookUpApiService: AladinBookLookUpApiService,
    private val json: Json
): AladinBookLookUpRemoteDataSource {
    override suspend fun loadBookPage(
        itemId: String
    ): AladinBookLookUpResponse {
        val rawResponse = aladinBookLookUpApiService.loadBookInfo(itemId)

        val semicolonRemovedResponse = rawResponse.trim().removeSuffix(";")

        return json.decodeFromString(semicolonRemovedResponse)
    }
}