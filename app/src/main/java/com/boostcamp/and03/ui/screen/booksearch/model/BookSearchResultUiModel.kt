package com.boostcamp.and03.ui.screen.booksearch.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResultUiModel(
    val title: String,
    val authors: ImmutableList<String>,
    val publisher: String,
    val thumbnail: String,
    val isbn: String // 국제표준도서번호, 이후 알라딘 상품 조회 API의 파라미터로 활용
)