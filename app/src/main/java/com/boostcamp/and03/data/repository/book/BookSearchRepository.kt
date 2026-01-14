package com.boostcamp.and03.data.repository.book

import androidx.paging.PagingData
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import com.boostcamp.and03.data.model.response.BookItem
import kotlinx.coroutines.flow.Flow

interface BookSearchRepository {

    suspend fun loadTotalSearchResultCount(query: String): Int

    fun loadSearchResults(query: String): Flow<PagingData<BookItem>>

    suspend fun loadBookPage(itemId: String): AladinBookLookUpResponse
}