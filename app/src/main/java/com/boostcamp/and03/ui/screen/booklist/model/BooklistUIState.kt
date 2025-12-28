package com.boostcamp.and03.ui.screen.booklist.model

data class BooklistUIState(
    val books: List<BookUIModel> = emptyList(),
    val isLoading: Boolean = false
)