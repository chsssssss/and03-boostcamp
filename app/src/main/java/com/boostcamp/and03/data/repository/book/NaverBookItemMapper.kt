package com.boostcamp.and03.data.repository.book

import com.boostcamp.and03.data.model.response.NaverBookItem
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel

fun NaverBookItem.toUiModel() = BookUIModel(
    title = title,
    author = author
        .split("^")
        .joinToString(", ") { it.trim() },
    publisher = publisher,
    thumbnail = thumbnail,
    isbn = isbn
)