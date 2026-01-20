package com.boostcamp.and03.ui.screen.canvasmemoform.model

import com.boostcamp.and03.ui.screen.canvasmemoform.CanvasMemoFormUiState

data class CanvasMemoFormUiModel(
    val title: String = "",
    val type: String = "CANVAS",
    val startPage: Int = 0,
    val endPage: Int = 0
)

fun CanvasMemoFormUiState.toUiModel() = CanvasMemoFormUiModel(
    title = title,
    type = "CANVAS",
    startPage = startPage.toInt(),
    endPage = endPage.toIntOrNull() ?: startPage.toInt()
)