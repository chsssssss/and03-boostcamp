package com.boostcamp.and03.data.repository.booksearch

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.boostcamp.and03.data.api.BookSearchApiConstants
import com.boostcamp.and03.data.datasource.paging.BookSearchPagingSource
import com.boostcamp.and03.data.datasource.remote.book_detail.BookDetailRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.search.BookSearchRemoteDataSource
import com.boostcamp.and03.data.model.response.BookSearchResultItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookSearchRepositoryImpl @Inject constructor(
    private val bookSearchRemoteDataSource: BookSearchRemoteDataSource,
    private val bookDetailRemoteDataSource: BookDetailRemoteDataSource
) : BookSearchRepository {

    override suspend fun loadTotalSearchResultCount(query: String): Int {
        val response = bookSearchRemoteDataSource.loadBooks(
            query = query,
            display = 1,
            start = 1
        )
        return response.total
    }

    override fun loadSearchResults(query: String): Flow<PagingData<BookSearchResultItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = BookSearchApiConstants.SIZE_DEFAULT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                BookSearchPagingSource(
                    remoteDataSource = bookSearchRemoteDataSource,
                    query = query
                )
            }
        ).flow
    }

    override suspend fun loadBookPage(itemId: String): Int {
        return bookDetailRemoteDataSource.loadBookPage(itemId = itemId).item.first().bookInfo.itemPage
    }
}