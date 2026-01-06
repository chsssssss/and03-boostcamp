package com.boostcamp.and03.ui.screen.booklist.model

import kotlinx.serialization.Serializable

@Serializable
data class BookUIModel(
    val title: String,
    val author: String,
    val publisher: String,
    val thumbnail: String,
    val isbn: String // 국제표준도서번호, 고유 id로 활용
) {
    val hasThumbnail: Boolean
        get() = thumbnail.isNotEmpty()
}