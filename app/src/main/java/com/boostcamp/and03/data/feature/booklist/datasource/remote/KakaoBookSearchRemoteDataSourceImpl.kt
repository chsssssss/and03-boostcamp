package com.boostcamp.and03.data.feature.booklist.datasource.remote

import com.boostcamp.and03.data.feature.booklist.model.KakaoBookSearchResponseDTO
import javax.inject.Inject

class KakaoBookSearchRemoteDataSourceImpl @Inject constructor(
    private val kakaoBookSearchService: KakaoBookSearchService
): KakaoBookSearchRemoteDataSource {
    override suspend fun loadBooks(
        query: String,
        page: Int,
        size: Int
    ): KakaoBookSearchResponseDTO =
        kakaoBookSearchService.loadBooks(
            query = query,
            page = page,
            size = size
        )
}