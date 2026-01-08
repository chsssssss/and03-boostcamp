package com.boostcamp.and03.data.repository.book

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.boostcamp.and03.data.api.NaverBookSearchApiConstants
import com.boostcamp.and03.data.datasource.paging.NaverBookSearchPagingSource
import com.boostcamp.and03.data.datasource.remote.AladinBookLookUpRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.BookRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.NaverBookSearchRemoteDataSource
import com.boostcamp.and03.data.model.request.toEntity
import com.boostcamp.and03.data.model.response.AladinBookLookUpResponse
import com.boostcamp.and03.data.model.response.NaverBookItem
import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val naverBookSearchRemoteDataSource: NaverBookSearchRemoteDataSource,
    private val aladinBookLookUpRemoteDataSource: AladinBookLookUpRemoteDataSource,
    private val bookRemoteDataSource: BookRemoteDataSource
): BookRepository {

    override suspend fun loadTotalResultCount(query: String): Int {
        val response = naverBookSearchRemoteDataSource.loadBooks(
            query = query,
            display = 1,
            start = 1
        )
        return response.total
    }

    override fun loadBooksPagingFlow(
        query: String
    ): Flow<PagingData<NaverBookItem>> {
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

    override suspend fun saveBook(
        userId: String,
        book: BookUiModel
    ) {
        bookRemoteDataSource.saveBook(
            userId = userId,
            book = book.toEntity()
        )
    }
}