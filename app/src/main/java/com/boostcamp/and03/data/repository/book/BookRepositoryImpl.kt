package com.boostcamp.and03.data.repository.book

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.boostcamp.and03.data.api.NaverBookSearchApiConstants
import com.boostcamp.and03.data.datasource.paging.NaverBookSearchPagingSource
import com.boostcamp.and03.data.datasource.remote.NaverBookSearchRemoteDataSource
import com.boostcamp.and03.data.model.response.NaverBookItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val remoteDataSource: NaverBookSearchRemoteDataSource
): BookRepository {

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
                    remoteDataSource = remoteDataSource,
                    query = query
                )
            }
        ).flow
    }
}