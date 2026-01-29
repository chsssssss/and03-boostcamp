package com.boostcamp.and03.ui.screen.textmemoform

data class TextMemoFormUiState(
    val title: String = "",
    val content: String = "",
    val startPage: String = "",
    val endPage: String = "",

    val originalTitle: String = "",
    val originalContent: String = "",
    val originalStartPage: String = "",
    val originalEndPage: String = "",

    val totalPage: Int = 0,
    val isSaving: Boolean = false,
    val isExitConfirmationDialogVisible: Boolean = false
) {
    val isValidPageRange: Boolean
        get() {
            val start = startPage.trim().toIntOrNull() ?: return false
            val max = totalPage

            if (start !in 1..max) return false

            val end = endPage.trim().toIntOrNull() ?: start
            if (end !in start..max) return false

            return true
        }

    val isSaveable: Boolean
        get() {
            return title.isNotBlank() &&
                    content.isNotBlank() &&
                    isValidPageRange
        }

    val isEdited: Boolean
        get() {
            return title != originalTitle ||
                    content != originalContent ||
                    startPage != originalStartPage ||
                    endPage != originalEndPage
        }
}