package com.boostcamp.and03.ui.screen.bookdetail.model

import com.boostcamp.and03.data.model.response.QuoteResponse

data class QuoteUiModel(
    val id: String,
    val content: String,
    val page: Int,
    val date: String
)

fun QuoteResponse.toUiModel(): QuoteUiModel {
    return QuoteUiModel(
        id = id,
        content = content,
        page = page,
        date = createdAt
    )
}