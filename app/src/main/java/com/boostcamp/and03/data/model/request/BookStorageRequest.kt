package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchResultUiModel

data class BookStorageRequest(
    val title: String = "",
    val authors: List<String> = emptyList(),
    val publisher: String = "",
    val isbn: String = "",
    val totalPage: Int = 0,
    val thumbnail: String = ""
)

fun BookSearchResultUiModel.toRequest() = BookStorageRequest(
    title = title,
    authors = authors,
    publisher = publisher,
    isbn = isbn,
    totalPage = totalPage,
    thumbnail = thumbnail
)