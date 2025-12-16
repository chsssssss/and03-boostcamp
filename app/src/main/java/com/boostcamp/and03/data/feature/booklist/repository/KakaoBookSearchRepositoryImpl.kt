package com.boostcamp.and03.data.feature.booklist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.boostcamp.and03.data.feature.booklist.datasource.paging.KakaoBookSearchPagingSource
import com.boostcamp.and03.data.feature.booklist.datasource.remote.KakaoBookSearchRemoteDataSource
import com.boostcamp.and03.data.feature.booklist.model.KakaoDocumentDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoBookSearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: KakaoBookSearchRemoteDataSource
) : KakaoBookSearchRepository {

    override fun loadBooksPagingFlow(
        query: String
    ): Flow<PagingData<KakaoDocumentDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = KAKAO_DEFAULT_PAGE_SIZE,
                initialLoadSize = KAKAO_DEFAULT_PAGE_SIZE * 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                KakaoBookSearchPagingSource(
                    remoteDataSource = remoteDataSource,
                    query = query
                )
            }
        ).flow
    }

    private companion object {
        const val KAKAO_DEFAULT_PAGE_SIZE = 10
    }
}