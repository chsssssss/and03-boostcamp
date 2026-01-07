package com.boostcamp.and03.ui.screen.bookdetail.model

data class MemoUiModel(
    val id: String,
    val memoType: MemoType,
    val startPage: Int,
    val endPage: Int,
    val date: String,
    val title: String,
    val content: String?,
)
