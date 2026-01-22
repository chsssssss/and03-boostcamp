package com.boostcamp.and03.ui.screen.bookdetail

sealed interface BookDetailEvent {

    data object NavigateBack : BookDetailEvent

    data class NavigateToTextMemoForm(
        val bookId: String,
        val memoId: String
    ) : BookDetailEvent

    data class NavigateToCanvasMemoForm(
        val bookId: String,
        val memoId: String
    ) : BookDetailEvent

    data class NavigateToCanvas(val memoId: String) : BookDetailEvent
}