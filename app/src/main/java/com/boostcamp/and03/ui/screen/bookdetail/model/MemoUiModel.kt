package com.boostcamp.and03.ui.screen.bookdetail.model

import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.MemoResponse
import com.boostcamp.and03.data.model.response.memo.TextMemoResponse

data class MemoUiModel(
    val id: String,
    val memoType: MemoType,
    val startPage: Int,
    val endPage: Int,
    val date: String,
    val title: String,
    val content: String?,
)

fun MemoResponse.toUiModel(): MemoUiModel {
    return when (this) {
        is TextMemoResponse -> {
            MemoUiModel(
                id = id,
                memoType = MemoType.TEXT,
                startPage = startPage,
                endPage = endPage,
                date = createdAt,
                title = title,
                content = content
            )
        }
        is CanvasMemoResponse -> {
            MemoUiModel(
                id = id,
                memoType = MemoType.CANVAS,
                startPage = startPage,
                endPage = endPage,
                date = createdAt,
                title = title,
                content = null
            )
        }
    }
}