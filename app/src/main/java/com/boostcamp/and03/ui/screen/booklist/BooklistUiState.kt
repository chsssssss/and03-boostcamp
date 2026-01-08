package com.boostcamp.and03.ui.screen.booklist

import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel

data class BooklistUiState(
    val books: List<BookUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)