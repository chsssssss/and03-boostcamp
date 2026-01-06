package com.boostcamp.and03.ui.screen.booksearch

import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BookSearchUiState(
    val query: String = "",
    val searchResults: ImmutableList<BookUIModel> = persistentListOf(),
    val selectedBookISBN: String? = null,
    val isLoading: Boolean = false,
    val isPaging: Boolean = false,
    val hasNextPage: Boolean = false,
) {
    val isSaveEnabled: Boolean
        get() = selectedBookISBN != null

    val showManualAddButton: Boolean
        get() = query.isBlank()
}