package com.boostcamp.and03.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Booklist : Route

    @Serializable
    data object BookSearch : Route

    @Serializable
    data object AddBook : Route

    @Serializable
    data object MyPage : Route

    @Serializable
    data class BookDetail(val bookId: String) : Route

    @Serializable
    data class CanvasMemo(
        val bookId: String,
        val memoId: String
    ) : Route

    @Serializable
    data class CharacterForm(
        val bookId: String,
        val characterId: String
    ) : Route

    @Serializable
    data class QuoteForm(
        val bookId: String,
        val quoteId: String
    ) : Route

    @Serializable
    data class TextMemoForm(
        val bookId: String,
        val memoId: String
    ) : Route

    @Serializable
    data class CanvasMemoForm(
        val bookId: String,
        val memoId: String
    ) : Route
}