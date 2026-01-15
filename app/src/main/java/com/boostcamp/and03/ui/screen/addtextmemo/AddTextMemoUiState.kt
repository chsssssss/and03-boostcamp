package com.boostcamp.and03.ui.screen.addtextmemo

data class AddTextMemoUiState(
    val title: String = "",
    val content: String = "",
    val startPage: String = "",
    val endPage: String = "",
    val totalPage: Int = 1000 // 동작 확인을 위한 임시 기본값 TODO: 실제 책의 페이지를 가져오게 수정 필요
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
}