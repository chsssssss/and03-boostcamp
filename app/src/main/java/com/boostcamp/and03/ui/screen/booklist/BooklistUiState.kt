package com.boostcamp.and03.ui.screen.booklist

import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BooklistUiState(
    val books: ImmutableList<BookUiModel> = persistentListOf(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)