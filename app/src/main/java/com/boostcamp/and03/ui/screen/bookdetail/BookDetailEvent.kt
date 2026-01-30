package com.boostcamp.and03.ui.screen.bookdetail

sealed interface BookDetailEvent {

    data object NavigateBack : BookDetailEvent

    data class NavigateToCharacterForm(
        val bookId: String,
        val characterId: String
    ) : BookDetailEvent

    data class NavigateToQuoteForm(
        val bookId: String,
        val quoteId: String,
        val totalPage: Int
    ) : BookDetailEvent

    data class NavigateToTextMemoForm(
        val bookId: String,
        val memoId: String,
        val totalPage: Int
    ) : BookDetailEvent

    data class NavigateToCanvasMemoForm(
        val bookId: String,
        val memoId: String,
        val totalPage: Int
    ) : BookDetailEvent

    data class NavigateToCanvas(
        val bookId: String,
        val memoId: String,
        val totalPage: Int
    ) : BookDetailEvent
}