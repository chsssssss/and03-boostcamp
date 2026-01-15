package com.boostcamp.and03.data.model.response

data class QuoteResponse(
    val id: String,
    val content: String,
    val page: Int,
    val createdAt: String,
)