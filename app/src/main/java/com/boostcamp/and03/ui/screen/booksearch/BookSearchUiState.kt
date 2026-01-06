package com.boostcamp.and03.ui.screen.booksearch

data class BookSearchUiState(
    val query: String = "",
    val selectedBookISBN: String? = null,
    val isLoading: Boolean = false
) {
    val isSaveEnabled: Boolean
        get() = selectedBookISBN != null

    val showManualAddButton: Boolean
        get() = query.isBlank()
}