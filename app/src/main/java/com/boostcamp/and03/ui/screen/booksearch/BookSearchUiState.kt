package com.boostcamp.and03.ui.screen.booksearch

data class BookSearchUiState(
    val query: String = "",
    val selectedBookISBN: String? = null
) {
    val isSaveEnabled: Boolean
        get() = selectedBookISBN != null
}