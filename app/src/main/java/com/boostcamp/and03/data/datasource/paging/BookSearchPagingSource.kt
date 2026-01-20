package com.boostcamp.and03.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.boostcamp.and03.data.datasource.remote.search.BookSearchRemoteDataSource
import com.boostcamp.and03.data.model.response.BookSearchResultItem

class BookSearchPagingSource(
    private val remoteDataSource: BookSearchRemoteDataSource,
    private val query: String
): PagingSource<Int, BookSearchResultItem>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, BookSearchResultItem> {

        val display = params.loadSize
        val start = params.key ?: 1

        val result = remoteDataSource.loadBooks(
            query = query,
            display = display,
            start = start,
        )

        return LoadResult.Page(
            data = result.items,
            prevKey = if (start == 1) null else maxOf(1, start - display),
            nextKey = if (result.hasNext) result.nextStart else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, BookSearchResultItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(state.config.pageSize)
                ?: closestPage?.nextKey?.minus(state.config.pageSize)
        }
    }

    companion object {
        private const val STARTING_ITEM_INDEX = 1
    }
}