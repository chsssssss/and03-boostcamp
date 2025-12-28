package com.boostcamp.and03.data.feature.booklist.repository

import androidx.paging.PagingData
import com.boostcamp.and03.data.feature.booklist.model.KakaoDocumentDTO
import kotlinx.coroutines.flow.Flow

interface KakaoBookSearchRepository {
    fun loadBooksPagingFlow(
        query: String
    ): Flow<PagingData<KakaoDocumentDTO>>
}