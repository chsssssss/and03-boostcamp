package com.boostcamp.and03.ui.screen.booklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.SearchTextField
import com.boostcamp.and03.ui.screen.booklist.component.BookCountText
import com.boostcamp.and03.ui.screen.booklist.component.BookGrid
import com.boostcamp.and03.ui.screen.booklist.model.BooklistUiState
import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BooklistRoute(
    viewModel: BooklistViewModel = hiltViewModel(),
    onBookClick: (BookUiModel) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BooklistScreen(
        uiState = uiState,
        onSearch = viewModel::onSearchQueryChange,
        onBookClick = onBookClick
    )
}

@Composable
private fun BooklistScreen(
    uiState: BooklistUiState,
    onSearch: (String) -> Unit,
    onBookClick: (BookUiModel) -> Unit = {}
) {
    val searchState = rememberTextFieldState(uiState.searchQuery)

    LaunchedEffect(searchState.text) {
        onSearch(searchState.text.toString())
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = "책 목록"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // ⭐ 핵심
                .padding(horizontal = And03Padding.PADDING_L)
        ) {
            Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

            SearchTextField(
                state = searchState,
                onSearch = { onSearch(searchState.text.toString()) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_L))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

            BookCountText(count = uiState.books.size)

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

            BookGrid(
                books = uiState.books,
                onBookClick = onBookClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BooklistScreenPreview() {
    val previewState = BooklistUiState(
        books = listOf(
            BookUiModel(
                title = "객체지향의 사실과 오해",
                authors = persistentListOf("조영호"),
                publisher = "위키북스",
                thumbnail = "",
                totalPage = 200,
                isbn = ""
            ),
            BookUiModel(
                title = "클린 아키텍처",
                authors = persistentListOf("로버트 C. 마틴"),
                publisher = "인사이트",
                thumbnail = "",
                totalPage = 200,
                isbn = ""
            )
        )
    )

    And03Theme {
        BooklistScreen(
            uiState = previewState,
            onSearch = {}
        )
    }
}
