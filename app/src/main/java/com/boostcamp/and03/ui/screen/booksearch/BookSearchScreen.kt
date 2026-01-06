package com.boostcamp.and03.ui.screen.booksearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.SearchResultItem
import com.boostcamp.and03.ui.component.SearchTextField
import com.boostcamp.and03.ui.component.SearchTopBar
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.theme.Dimensions
import kotlinx.coroutines.flow.flowOf

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
        onBackClick = onBackClick,
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
    onBackClick: () -> Unit,
    onItemClick: (BookUIModel) -> Unit,
    onSaveClick: () -> Unit,
    onManualAddClick: () -> Unit,
    modifier: Modifier= Modifier
) {
    val searchTextState = remember { TextFieldState() }

    Column(modifier = modifier.fillMaxSize()) {
        SearchTopBar(
            title = stringResource(R.string.book_search_title),
            onBackClick = onBackClick,
            onSaveClick = onSaveClick,
            isSaveEnabled = uiState.isSaveEnabled
        )

        SearchTextField(
            state = searchTextState,
            onSearch = { onQueryChange(searchTextState.text.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.PADDING_M)
        )

        when {
            uiState.query.isBlank() -> {
                BookSearchEmptySection(
                    message = stringResource(R.string.book_search_empty_before_query),
                    onManualAddClick = onManualAddClick
                )
            }

            searchResults.itemCount == 0 -> {
                BookSearchEmptySection(
                    message = stringResource(R.string.book_search_empty_after_query),
                    onManualAddClick = onManualAddClick
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = Dimensions.PADDING_L),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.PADDING_M)
                ) {
                    items(
                        count = searchResults.itemCount,
                        key = { index ->
                            searchResults[index]?.isbn ?: index
                        }
                    ) { index ->
                        val book = searchResults[index] ?: return@items

                        SearchResultItem(
                            thumbnail = book.thumbnail,
                            title = book.title,
                            author = book.author,
                            publisher = book.publisher,
                            isSelected = book.isbn == uiState.selectedBookISBN,
                            onClick = { onItemClick(book) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookSearchEmptySection(
    message: String,
    onManualAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = And03Theme.typography.bodyLarge,
            color = And03Theme.colors.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.PADDING_L))

        And03Button(
            text = stringResource(R.string.book_search_button_text),
            onClick = onManualAddClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookSearchScreenPreview() {
    val uiState = BookSearchUiState(
        query = "안드로이드",
        selectedBookISBN = "222"
    )

    val previewBooks = listOf(
        BookUIModel(
            isbn = "111",
            title = "이펙티브 코틀린",
            author = "마르친 모스칼라",
            publisher = "인사이트",
            thumbnail = ""
        ),
        BookUIModel(
            isbn = "222",
            title = "안드로이드 Compose 완벽 가이드",
            author = "Compose 팀",
            publisher = "구글",
            thumbnail = ""
        )
    )

    val pagingItems = flowOf(
        PagingData.from(previewBooks)
    ).collectAsLazyPagingItems()

    And03Theme {
        BookSearchScreen(
            uiState = uiState,
            searchResults = pagingItems,
            onQueryChange = {},
            onBackClick = {},
            onItemClick = {},
            onSaveClick = {},
            onManualAddClick = {}
        )
    }
}