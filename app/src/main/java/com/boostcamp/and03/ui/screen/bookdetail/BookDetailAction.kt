package com.boostcamp.and03.ui.screen.bookdetail

sealed interface BookDetailAction {

    data object OnBackClick: BookDetailAction

    data class OnRetryClick(val tabIndex: Int): BookDetailAction

    data class OnTabSelect(val tabIndex: Int): BookDetailAction

    data class OnOpenCharacterForm(
        val bookId: String,
        val characterId: String
    ): BookDetailAction

    data class OnOpenQuoteForm(
        val bookId: String,
        val quoteId: String
    ): BookDetailAction

    data class DeleteCharacter(val characterId: String): BookDetailAction

    data class DeleteQuote(val quoteId: String): BookDetailAction

    data class DeleteMemo(val memoId: String): BookDetailAction

    data class OnOpenTextMemoForm(
        val bookId: String,
        val memoId: String
    ): BookDetailAction

    data class OnOpenCanvasMemoForm(
        val bookId: String,
        val memoId: String
    ): BookDetailAction

    data class OnCanvasMemoClick(val memoId: String): BookDetailAction
}