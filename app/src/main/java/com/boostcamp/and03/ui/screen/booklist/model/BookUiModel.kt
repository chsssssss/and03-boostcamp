package com.boostcamp.and03.ui.screen.booklist.model

import com.boostcamp.and03.data.model.request.BookEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class BookUiModel(
    val title: String,
    val authors: ImmutableList<String>,
    val publisher: String,
    val thumbnail: String,
    val totalPage: Int,
    val isbn: String // 국제표준도서번호, 고유 id로 활용
) {
    val hasThumbnail: Boolean
        get() = thumbnail.isNotEmpty()
}

fun BookEntity.toUiModel() = BookUiModel(
    title = title,
    authors = authors.toImmutableList(),
    publisher = publisher,
    thumbnail = thumbnail,
    isbn = isbn,
    totalPage = totalPage
)