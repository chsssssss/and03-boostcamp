package com.boostcamp.and03.data.feature.booklist.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.boostcamp.and03.data.feature.booklist.datasource.remote.KakaoBookSearchRemoteDataSource
import com.boostcamp.and03.data.feature.booklist.model.KakaoDocumentDTO

class KakaoBookSearchPagingSource(
    val remoteDataSource: KakaoBookSearchRemoteDataSource,
    val query: String
) : PagingSource<Int, KakaoDocumentDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KakaoDocumentDTO> {
        val page = params.key ?: KAKAO_STARTING_PAGE_INDEX
        val size = params.loadSize

        val result = remoteDataSource.loadBooks(
            query = query,
            page = page,
            size = size
        )

        return LoadResult.Page(
            data = result.documents,
            prevKey = if (page == KAKAO_STARTING_PAGE_INDEX) null else page - 1,
            nextKey = if (result.meta.isEnd) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, KakaoDocumentDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val KAKAO_STARTING_PAGE_INDEX = 1
    }
}