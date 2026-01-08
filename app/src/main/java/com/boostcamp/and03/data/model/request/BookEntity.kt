package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel

data class BookEntity(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val isbn: String,
    val totalPage: Int,
    val thumbnail: String
)

fun BookUiModel.toEntity() = BookEntity(
    title = title,
    authors = authors,
    publisher = publisher,
    isbn = isbn,
    totalPage = totalPage,
    thumbnail = thumbnail
)