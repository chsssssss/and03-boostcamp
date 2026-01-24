package com.boostcamp.and03.ui.screen.bookdetail.model

import com.boostcamp.and03.data.model.response.QuoteResponse
import com.boostcamp.and03.ui.screen.quoteform.QuoteFormUiState

data class QuoteUiModel(
    val id: String = "",
    val content: String = "",
    val page: Int = 0,
    val date: String = ""
)

fun QuoteResponse.toUiModel(): QuoteUiModel {
    return QuoteUiModel(
        id = id,
        content = content,
        page = page,
        date = createdAt
    )
}

fun QuoteFormUiState.toUiModel() = QuoteUiModel(
    content = quote,
    page = page.toInt(),
)