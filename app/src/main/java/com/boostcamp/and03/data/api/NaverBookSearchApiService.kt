package com.boostcamp.and03.data.api

import com.boostcamp.and03.data.model.response.NaverBookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

object NaverBookSearchApiConstants {
    const val START_DEFAULT = 1
    const val START_MAX = 1000
    const val SIZE_DEFAULT = 10
    const val SIZE_MAX = 100
    const val SORT_SIMILARITY = "sim"
    const val SORT_DATE = "date"
}

interface NaverBookSearchApiService {
    @GET("v1/search/book.json")
    suspend fun loadBooks(
        @Query("query") query: String,
        @Query("display") display: Int = NaverBookSearchApiConstants.SIZE_DEFAULT,
        @Query("start") start: Int = NaverBookSearchApiConstants.START_DEFAULT,
        @Query("sort") sort: String = NaverBookSearchApiConstants.SORT_SIMILARITY
    ): NaverBookSearchResponse
}
