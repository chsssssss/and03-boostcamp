package com.boostcamp.and03.ui.screen.bookdetail

import com.boostcamp.and03.ui.screen.bookdetail.model.BookDetailTab

sealed interface BookDetailAction {

    data object OnBackClick: BookDetailAction

    data object OnRetryBookInfo : BookDetailAction

    data class OnRetryTab(val tab: BookDetailTab): BookDetailAction

    data class OnTabSelect(val tab: BookDetailTab): BookDetailAction

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

    data class OnCanvasMemoClick(
        val bookId: String,
        val memoId: String,
        val totalPage: Int
    ): BookDetailAction
}