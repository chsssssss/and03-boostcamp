package com.boostcamp.and03.ui.screen.booklist.model

data class BooklistUiState(
    val books: List<BookUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)