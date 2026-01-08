package com.boostcamp.and03.data.repository.book

import androidx.paging.PagingData
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import com.boostcamp.and03.data.model.response.NaverBookItem
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun loadTotalResultCount(
        query: String
    ): Int

    fun loadBooksPagingFlow(
        query: String
    ): Flow<PagingData<NaverBookItem>>

    suspend fun loadBookPage(
        itemId: String
    ): AladinBookLookUpResponse
}