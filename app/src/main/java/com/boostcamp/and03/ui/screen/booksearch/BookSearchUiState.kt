package com.boostcamp.and03.ui.screen.booksearch

import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchResultUiModel

data class BookSearchUiState(
    val query: String = "",
    val selectedBook: BookSearchResultUiModel? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
) {
    val isSaveEnabled: Boolean
        get() = selectedBook != null && !isSaving
}