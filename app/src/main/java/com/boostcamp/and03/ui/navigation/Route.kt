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
    data object CanvasMemo : Route

    @Serializable
    data class Canvas(val memoId: String) : Route

    @Serializable
    data object MemoEdit: Route // 메모 관계도 화면에서 넘어갈 수 있느 메모 편집 화면, TODO: 추후 수정 필요

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