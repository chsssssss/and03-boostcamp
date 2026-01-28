package com.boostcamp.and03.ui.screen.booksearch

data class BookSearchUiState(
    val query: String = "",
    val selectedResultIndex: Int? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
) {
    val isSaveEnabled: Boolean
        get() = selectedResultIndex != null && !isSaving
}