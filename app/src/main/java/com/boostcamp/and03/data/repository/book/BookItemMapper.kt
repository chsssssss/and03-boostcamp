package com.boostcamp.and03.data.repository.book

import com.boostcamp.and03.data.model.response.BookItem
import com.boostcamp.and03.ui.screen.booksearch.model.SearchResultUiModel
import kotlinx.collections.immutable.toImmutableList

fun BookItem.toUiModel() = SearchResultUiModel(
    title = title,
    authors = author.split("^").toImmutableList(),
    publisher = publisher,
    thumbnail = thumbnail,
    isbn = isbn
)