package com.boostcamp.and03.ui.screen.booksearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.screen.booksearch.component.SearchResultItem
import com.boostcamp.and03.ui.component.SearchTextField
import com.boostcamp.and03.ui.screen.booksearch.model.SearchResultUiModel
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf

@Composable
fun BookSearchRoute(
    onBackClick: () -> Unit,
    viewModel: BookSearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchResults = viewModel.pagingBooksFlow.collectAsLazyPagingItems()
    val totalResultCount by viewModel.totalResultCountFlow.collectAsStateWithLifecycle(0)

    BookSearchScreen(
        uiState = uiState,
        searchResults = searchResults,
        totalResultCount = totalResultCount,
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
    searchResults: LazyPagingItems<SearchResultUiModel>,
    totalResultCount: Int,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onItemClick: (SearchResultUiModel) -> Unit,
    onSaveClick: () -> Unit,
    onManualAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchTextState = remember { TextFieldState(uiState.query) }
    val isQueryEmpty = uiState.query.isBlank()
    val refreshState = searchResults.loadState.refresh

    LaunchedEffect(Unit) {
        snapshotFlow { searchTextState.text.toString() }
            .collect { text ->
                onQueryChange(text)
            }
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(R.string.book_search_title),
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = onSaveClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_check_filled),
                            contentDescription = stringResource(R.string.content_description_save_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchTextField(
                state = searchTextState,
                onSearch = { onQueryChange(searchTextState.text.toString()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(And03Padding.PADDING_M)
            )

            when {
                isQueryEmpty -> {
                    BookSearchResultEmptySection(
                        message = stringResource(R.string.book_search_empty_before_query),
                        buttonText = stringResource(R.string.book_search_manual_add_button_text),
                        onButtonClick = onManualAddClick
                    )
                }

                refreshState is LoadState.Loading -> {
                    BookSearchResultEmptySection(
                        message = "",
                        buttonText = "",
                        onButtonClick = {},
                        isLoading = true
                    )
                }

                refreshState is LoadState.Error -> {
                    BookSearchResultEmptySection(
                        message = stringResource(R.string.book_search_error_text),
                        buttonText = stringResource(R.string.book_search_retry_button_text),
                        onButtonClick = { searchResults.retry() }
                    )
                }

                refreshState is LoadState.NotLoading && searchResults.itemCount == 0 -> {
                    BookSearchResultEmptySection(
                        message = stringResource(R.string.book_search_empty_after_query),
                        buttonText = stringResource(R.string.book_search_manual_add_button_text),
                        onButtonClick = onManualAddClick
                    )
                }

                else -> {
                    BookSearchResultListSection(
                        searchResults = searchResults,
                        selectedBookISBN = uiState.selectedBookISBN,
                        totalResultCount = totalResultCount,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
private fun BookSearchResultListSection(
    searchResults: LazyPagingItems<SearchResultUiModel>,
    selectedBookISBN: String?,
    totalResultCount: Int,
    onItemClick: (SearchResultUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = And03Padding.PADDING_L),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M)
    ) {
        SearchResultCountText(count = totalResultCount)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M)
        ) {
            items(
                count = searchResults.itemCount,
                key = searchResults.itemKey { it.isbn }
            ) { index ->
                val book = searchResults[index] ?: return@items
                SearchResultItem(
                    thumbnail = book.thumbnail,
                    title = book.title,
                    authors = book.authors,
                    publisher = book.publisher,
                    isSelected = book.isbn == selectedBookISBN,
                    onClick = { onItemClick(book) }
                )
            }
        }
    }
}

@Composable
private fun BookSearchResultEmptySection(
    message: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = message,
                style = And03Theme.typography.bodyLarge,
                color = And03Theme.colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_L))

            And03Button(
                text = buttonText,
                onClick = onButtonClick
            )
        }
    }
}

@Composable
private fun SearchResultCountText(count: Int) {
    val fullText = stringResource(R.string.book_search_result_count_text, count)
    val countText = count.toString()

    Text(
        text = buildAnnotatedString {
            val start = fullText.indexOf(countText)

            append(fullText)

            if (start >= 0) {
                addStyle(
                    style = SpanStyle(
                        color = And03Theme.colors.primary,
                        fontSize = And03Theme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    ),
                    start = start,
                    end = start + countText.length
                )
            }
        },
        style = And03Theme.typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
private fun BookSearchScreenPreview() {
    val uiState = BookSearchUiState(
        query = "안드로이드",
        selectedBookISBN = "222"
    )

    val previewBooks = listOf(
        SearchResultUiModel(
            isbn = "111",
            title = "이펙티브 코틀린",
            authors = persistentListOf("마르친 모스칼라"),
            publisher = "인사이트",
            thumbnail = ""
        ),
        SearchResultUiModel(
            isbn = "222",
            title = "안드로이드 Compose 완벽 가이드",
            authors = persistentListOf("Compose 팀"),
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
            totalResultCount = 2,
            onQueryChange = {},
            onBackClick = {},
            onItemClick = {},
            onSaveClick = {},
            onManualAddClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookSearchScreenEmptyBeforeQueryPreview() {
    val uiState = BookSearchUiState(
        query = "",
        selectedBookISBN = null
    )

    val pagingItems = flowOf(
        PagingData.empty<SearchResultUiModel>()
    ).collectAsLazyPagingItems()

    And03Theme {
        BookSearchScreen(
            uiState = uiState,
            searchResults = pagingItems,
            totalResultCount = 0,
            onQueryChange = {},
            onBackClick = {},
            onItemClick = {},
            onSaveClick = {},
            onManualAddClick = {}
        )
    }
}