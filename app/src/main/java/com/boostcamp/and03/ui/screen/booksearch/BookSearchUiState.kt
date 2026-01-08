package com.boostcamp.and03.ui.screen.booksearch

import com.boostcamp.and03.ui.screen.booksearch.model.SearchResultUiModel

data class BookSearchUiState(
    val query: String = "",
    val selectedBook: SearchResultUiModel? = null,
    val totalResultCount: Int = 0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
) {
    val isSaveEnabled: Boolean
        get() = selectedBook != null && !isSaving
}