package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.textmemoform.TextMemoFormUiState
import com.boostcamp.and03.ui.screen.textmemoform.model.TextMemoFormUiModel
import kotlinx.serialization.Serializable

@Serializable
data class TextMemoRequest(
    val title: String = "",
    val content: String = "",
    val type: String = "TEXT",
    val startPage: Int = 0,
    val endPage: Int = 0,
)

fun TextMemoFormUiModel.toRequest() = TextMemoRequest(
    title = title,
    content = content,
    type = "TEXT",
    startPage = startPage,
    endPage = endPage,
)