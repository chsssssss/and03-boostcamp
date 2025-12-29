package com.boostcamp.and03.data.api

import com.boostcamp.and03.data.model.response.NaverBookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

private object NaverBookSearchServiceValue {
    const val PAGE_DEFAULT = 1
    const val PAGE_MAX = 1000
    const val SIZE_DEFAULT = 10
    const val SIZE_MAX = 100
    const val SORT_SIMILARITY = "sim"
    const val SORT_DATE = "date"
}

interface NaverBookSearchService {
    @GET("v1/search/book.json")
    suspend fun loadBooks(
        @Query("query") query: String,
        @Query("display") display: Int = NaverBookSearchServiceValue.SIZE_DEFAULT,
        @Query("start") start: Int = NaverBookSearchServiceValue.PAGE_DEFAULT,
        @Query("sort") sort: String = NaverBookSearchServiceValue.SORT_SIMILARITY
    ): NaverBookSearchResponse
}
