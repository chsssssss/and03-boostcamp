package com.boostcamp.and03.data.model.response

data class BookStorageResponse(
    val id: String = "",
    val author: List<String> = emptyList(),
    val isbn: String = "",
    val publisher: String = "",
    val thumbnail: String = "",
    val title: String = "",
    val totalPage: Int = 0
)