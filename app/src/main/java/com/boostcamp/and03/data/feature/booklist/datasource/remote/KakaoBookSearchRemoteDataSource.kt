package com.boostcamp.and03.data.feature.booklist.datasource.remote

import com.boostcamp.and03.data.feature.booklist.model.KakaoBookSearchResponseDTO

interface KakaoBookSearchRemoteDataSource {
    suspend fun loadBooks(
        query: String,
        page: Int,
        size: Int
    ): KakaoBookSearchResponseDTO
}