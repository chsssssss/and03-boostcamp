package com.boostcamp.and03.ui.navigation

import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel
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
    data class BookDetail(val isbn: String) : Route

    @Serializable
    data class Canvas(val memoId: String) : Route

    @Serializable
    data object MemoEdit: Route
}