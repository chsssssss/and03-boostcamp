package com.boostcamp.and03.data.model.response.memo

sealed interface MemoResponse {
    val id: String
    val title: String
    val createdAt: String
    val type: String
}