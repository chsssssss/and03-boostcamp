package com.boostcamp.and03.data.datasource.remote.search.naver

import com.boostcamp.and03.data.model.response.NaverBookSearchResponse

interface NaverBookSearchRemoteDataSource {
    suspend fun loadBooks(
        query: String,
        display: Int,
        start: Int
    ): NaverBookSearchResponse
}