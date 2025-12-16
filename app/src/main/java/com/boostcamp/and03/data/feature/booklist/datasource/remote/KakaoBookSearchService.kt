package com.boostcamp.and03.data.feature.booklist.datasource.remote

import com.boostcamp.and03.data.feature.booklist.model.KakaoBookSearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

private object KakaoBookSearchServiceValue {
    const val PAGE_DEFAULT = 1
    const val PAGE_MAX = 50
    const val SIZE_DEFAULT = 10
    const val SIZE_MAX = 50
}

interface KakaoBookSearchService {
    @GET("v3/search/book")
    suspend fun loadBooks(
        @Query("query") query: String,
        @Query("page") page: Int = KakaoBookSearchServiceValue.PAGE_DEFAULT,
        @Query("size") size: Int = KakaoBookSearchServiceValue.SIZE_DEFAULT
    ): KakaoBookSearchResponseDTO
}