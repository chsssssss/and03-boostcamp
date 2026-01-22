package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import kotlinx.serialization.Serializable

@Serializable
data class QuoteRequest(
    val content: String = "",
    val page: Int = 0,
)

fun QuoteUiModel.toRequest() = QuoteRequest(
    content = content.trim(),
    page = page
)