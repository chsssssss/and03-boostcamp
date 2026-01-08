package com.boostcamp.and03.data.repository.book

import com.boostcamp.and03.data.model.response.NaverBookItem
import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchResultUiModel
import kotlinx.collections.immutable.toImmutableList

fun NaverBookItem.toUiModel() = BookSearchResultUiModel(
    title = title,
    authors = author.split("^").toImmutableList(),
    publisher = publisher,
    thumbnail = thumbnail,
    isbn = isbn
)