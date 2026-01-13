package com.boostcamp.and03.ui.screen.addtextmemo

data class AddTextMemoUiState(
    val title: String = "",
    val content: String = "",
    val startPage: Int = 0,
    val endPage: Int = 0,
    val isSaveable: Boolean = false
)