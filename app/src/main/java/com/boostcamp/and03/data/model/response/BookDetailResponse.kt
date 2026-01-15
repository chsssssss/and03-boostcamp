package com.boostcamp.and03.data.model.response

data class BookDetailResponse(
    val id: String,
    val title: String,
    val author: List<String>,
    val publisher: String,
    val thumbnail: String,
)