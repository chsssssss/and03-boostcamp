package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.api.NaverBookSearchApiService
import com.boostcamp.and03.data.model.response.NaverBookSearchResponse
import javax.inject.Inject

class NaverBookSearchRemoteDataSourceImpl @Inject constructor(
    private val naverBookSearchService: NaverBookSearchApiService
) : NaverBookSearchRemoteDataSource {
    override suspend fun loadBooks(
        query: String,
        display: Int,
        start: Int
    ): NaverBookSearchResponse =
        naverBookSearchService.loadBooks(
            query = query,
            display = display,
            start = start
        )
}