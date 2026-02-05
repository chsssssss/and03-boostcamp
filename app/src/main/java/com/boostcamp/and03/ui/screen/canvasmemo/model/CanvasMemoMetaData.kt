package com.boostcamp.and03.ui.screen.canvasmemo.model

import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse

data class CanvasMemoMetaData(
    val id: String = "",
    val title: String = "",
    val type: String = "CANVAS",
    val startPage: Int = 0,
    val endPage: Int = 0
)

fun CanvasMemoResponse.getMetaData() = CanvasMemoMetaData(
    id = id,
    title = title,
    type = type,
    startPage = startPage,
    endPage = endPage
)