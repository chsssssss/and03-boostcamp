package com.boostcamp.and03.data.repository.book

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.boostcamp.and03.data.api.NaverBookSearchApiConstants
import com.boostcamp.and03.data.datasource.paging.NaverBookSearchPagingSource
import com.boostcamp.and03.data.datasource.remote.BookDetailRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.BookSearchRemoteDataSource
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import com.boostcamp.and03.data.model.response.BookItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookSearchRepositoryImpl @Inject constructor(
    private val naverBookSearchRemoteDataSource: BookSearchRemoteDataSource,
    private val aladinBookLookUpRemoteDataSource: BookDetailRemoteDataSource
): BookSearchRepository {

    override suspend fun loadTotalSearchResultCount(query: String): Int {
        val response = naverBookSearchRemoteDataSource.loadBooks(
            query = query,
            display = 1,
            start = 1
        )
        return response.total
    }

    override fun loadSearchResults(query: String): Flow<PagingData<BookItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NaverBookSearchApiConstants.SIZE_DEFAULT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NaverBookSearchPagingSource(
                    remoteDataSource = naverBookSearchRemoteDataSource,
                    query = query
                )
            }
        ).flow
    }

    override suspend fun loadBookPage(
        itemId: String
    ): AladinBookLookUpResponse {
        return aladinBookLookUpRemoteDataSource.loadBookPage(
            itemId = itemId
        )
    }
}