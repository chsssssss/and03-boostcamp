package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.textmemoform.TextMemoFormUiState
import kotlinx.serialization.Serializable

@Serializable
data class TextMemoRequest(
    val title: String = "",
    val content: String = "",
    val type: String = "TEXT",
    val startPage: Int = 0,
    val endPage: Int = 0,
)

fun TextMemoFormUiState.toRequest(): TextMemoRequest {
    val start = startPage.trim().toInt()
    val end = endPage.trim().toIntOrNull() ?: start

    return TextMemoRequest(
        title = title.trim(),
        content = content.trim(),
        type = "TEXT",
        startPage = start,
        endPage = end
    )
}