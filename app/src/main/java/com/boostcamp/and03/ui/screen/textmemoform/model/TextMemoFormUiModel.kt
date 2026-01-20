package com.boostcamp.and03.ui.screen.textmemoform.model

import com.boostcamp.and03.ui.screen.textmemoform.TextMemoFormUiState

data class TextMemoFormUiModel(
    val title: String = "",
    val content: String = "",
    val type: String = "TEXT",
    val startPage: Int = 0,
    val endPage: Int = 0
)

fun TextMemoFormUiState.toUiModel() = TextMemoFormUiModel(
    title = title,
    content = content,
    type = "TEXT",
    startPage = startPage.toInt(),
    endPage = endPage.toIntOrNull() ?: startPage.toInt()
)