package com.boostcamp.and03.ui.screen.booklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.ui.component.SearchTextField
import com.boostcamp.and03.ui.screen.booklist.component.BookCountText
import com.boostcamp.and03.ui.screen.booklist.component.BookGrid
import com.boostcamp.and03.ui.screen.booklist.component.BooklistTitle
import com.boostcamp.and03.ui.screen.booklist.model.BooklistUIState
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BooklistRoute(
    viewModel: BooklistViewModel = hiltViewModel(),
    onBookClick: (BookUIModel) -> Unit = {}
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
    uiState: BooklistUIState,
    onSearch: (String) -> Unit,
    onBookClick: (BookUIModel) -> Unit = {}
) {
    val searchState = rememberTextFieldState(uiState.searchQuery)

    LaunchedEffect(searchState.text) {
        onSearch(searchState.text.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = And03Padding.PADDING_L)
    ) {
        Spacer(Modifier.height(And03Spacing.SPACE_M))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            BooklistTitle()
        }

        Spacer(Modifier.height(And03Spacing.SPACE_L))

        SearchTextField(
            state = searchState,
            onSearch = { onSearch(searchState.text.toString()) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(And03Spacing.SPACE_L))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )

        Spacer(Modifier.height(And03Spacing.SPACE_M))

        BookCountText(count = uiState.books.size)

        Spacer(Modifier.height(And03Spacing.SPACE_M))

        BookGrid(
            books = uiState.books,
            onBookClick = onBookClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BooklistScreenPreview() {
    val previewState = BooklistUIState(
        books = listOf(
            BookUIModel(
                title = "객체지향의 사실과 오해",
                authors = persistentListOf("조영호"),
                publisher = "위키북스",
                thumbnail = "",
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
