package com.boostcamp.and03.ui.screen.booklist.model

data class BooklistUIState(
    val books: List<BookUIModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)