package com.boostcamp.and03.ui.screen.booklist.model

import com.boostcamp.and03.data.model.response.BookStorageResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class BookUiModel(
    val id: String,
    val title: String,
    val authors: ImmutableList<String>,
    val publisher: String,
    val thumbnail: String,
    val totalPage: Int,
    val isbn: String // 국제표준도서번호
) {
    val hasThumbnail: Boolean
        get() = thumbnail.isNotEmpty()
}

fun BookStorageResponse.toUiModel() = BookUiModel(
    id = id,
    title = title,
    authors = author.toImmutableList(),
    publisher = publisher,
    thumbnail = thumbnail,
    isbn = isbn,
    totalPage = totalPage
)