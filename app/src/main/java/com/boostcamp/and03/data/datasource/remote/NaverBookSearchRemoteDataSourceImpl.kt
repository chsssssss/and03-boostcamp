package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.api.NaverBookSearchService
import com.boostcamp.and03.data.model.response.NaverBookSearchResponse
import javax.inject.Inject

class NaverBookSearchRemoteDataSourceImpl @Inject constructor(
    private val naverBookSearchService: NaverBookSearchService
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