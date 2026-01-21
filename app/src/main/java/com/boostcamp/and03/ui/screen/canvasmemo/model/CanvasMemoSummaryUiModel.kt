package com.boostcamp.and03.ui.screen.canvasmemo.model

import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse

data class CanvasMemoSummaryUiModel (
    val memoId: String,
    val startPage: Int,
    val endPage: Int,
    val title: String
)

fun CanvasMemoResponse.toSummaryUiModel() = CanvasMemoSummaryUiModel(
    memoId = id,
    startPage = startPage,
    endPage = endPage,
    title = title
)