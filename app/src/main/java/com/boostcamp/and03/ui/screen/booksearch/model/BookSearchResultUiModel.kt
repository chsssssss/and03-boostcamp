package com.boostcamp.and03.ui.screen.booksearch.model

import com.boostcamp.and03.data.model.response.BookSearchResultItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResultUiModel(
    val title: String,
    val authors: ImmutableList<String>,
    val publisher: String,
    val thumbnail: String,
    val isbn: String, // 국제표준도서번호, 이후 알라딘 상품 조회 API의 파라미터로 활용
    val totalPage: Int = 0 // 전체 페이지는 저장 시 알라딘 API로부터 따로 가져오므로 기본값을 0으로 설정
)

fun BookSearchResultItem.toUiModel() = BookSearchResultUiModel(
    title = title,
    authors = author.split("^").toImmutableList(),
    publisher = publisher,
    thumbnail = thumbnail,
    isbn = isbn
)