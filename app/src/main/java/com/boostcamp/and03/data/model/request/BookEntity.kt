package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel
import kotlinx.collections.immutable.toImmutableList

data class BookEntity(
    val title: String = "",
    val authors: List<String> = emptyList(),
    val publisher: String = "",
    val isbn: String = "",
    val totalPage: Int = 0,
    val thumbnail: String = ""
)

fun BookUiModel.toEntity() = BookEntity(
    title = title,
    authors = authors,
    publisher = publisher,
    isbn = isbn,
    totalPage = totalPage,
    thumbnail = thumbnail
)