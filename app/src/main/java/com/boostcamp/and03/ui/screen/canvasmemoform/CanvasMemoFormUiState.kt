package com.boostcamp.and03.ui.screen.canvasmemoform

data class CanvasMemoFormUiState(
    val title: String = "",
    val startPage: String = "",
    val endPage: String = "",
    val totalPage: Int = 0,
    val isSaving: Boolean = false
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
            return title.isNotBlank() && isValidPageRange
        }
}