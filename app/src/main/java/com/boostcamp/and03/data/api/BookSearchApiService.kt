package com.boostcamp.and03.data.api

import com.boostcamp.and03.data.model.response.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookSearchApiService {
    @GET("v1/search/book.json")
    suspend fun loadBooks(
        @Query("query") query: String,
        @Query("display") display: Int = BookSearchApiConstants.SIZE_DEFAULT,
        @Query("start") start: Int = BookSearchApiConstants.START_DEFAULT,
        @Query("sort") sort: String = BookSearchApiConstants.SORT_SIMILARITY
    ): BookSearchResponse
}
