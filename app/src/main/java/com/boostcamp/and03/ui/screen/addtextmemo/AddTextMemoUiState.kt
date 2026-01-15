package com.boostcamp.and03.ui.screen.addtextmemo

data class AddTextMemoUiState(
    val title: String = "",
    val content: String = "",
    val startPage: String = "",
    val endPage: String = "",
    val isSaveable: Boolean = false
)