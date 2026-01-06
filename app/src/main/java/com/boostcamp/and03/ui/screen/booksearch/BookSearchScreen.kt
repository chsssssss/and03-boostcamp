package com.boostcamp.and03.ui.screen.booksearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel

@Composable
fun BookSearchRoute(
    onBackClick: () -> Unit,
    viewModel: BookSearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchResults = viewModel.pagingBooksFlow.collectAsLazyPagingItems()

    BookSearchScreen(
        uiState = uiState,
        searchResults = searchResults,
        onQueryChange = viewModel::changeQuery,
        onItemClick = viewModel::clickItem,
        onSaveClick = { /* TODO: viewModel::saveItem 구현 */ },
        onManualAddClick = { /* TODO: 책 추가 화면 구현 및 이동 동작 구현 */ }
    )
}

@Composable
private fun BookSearchScreen(
    uiState: BookSearchUiState,
    searchResults: LazyPagingItems<BookUIModel>,
    onQueryChange: (String) -> Unit,
    onItemClick: (BookUIModel) -> Unit,
    onSaveClick: (BookUIModel) -> Unit,
    onManualAddClick: () -> Unit,
) {

}