package com.boostcamp.and03.data.api

import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import retrofit2.http.GET
import retrofit2.http.Query

private object AladinBookLookUpApiValues {
    const val ITEM_ID_TYPE_ISBN13 = "ISBN13"
    const val OUTPUT_METHOD_JSON = "JS"
}

interface AladinBookLookUpApiService {
    @GET("ttb/api/ItemLookUp.aspx")
    suspend fun loadBookInfo(
        @Query("ItemId") itemId: String,
        @Query("ItemIdType") itemIdType: String = AladinBookLookUpApiValues.ITEM_ID_TYPE_ISBN13,
        @Query("Output") output: String = AladinBookLookUpApiValues.OUTPUT_METHOD_JSON
    ): AladinBookLookUpResponse
}