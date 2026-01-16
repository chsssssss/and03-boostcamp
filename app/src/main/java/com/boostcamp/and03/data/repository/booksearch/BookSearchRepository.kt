package com.boostcamp.and03.data.repository.booksearch

import androidx.paging.PagingData
import com.boostcamp.and03.data.model.response.BookSearchResultItem
import kotlinx.coroutines.flow.Flow

interface BookSearchRepository {

    suspend fun loadTotalSearchResultCount(query: String): Int

    fun loadSearchResults(query: String): Flow<PagingData<BookSearchResultItem>>

    suspend fun loadBookPage(itemId: String): Int
}