package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.canvasmemoform.model.CanvasMemoFormUiModel
import kotlinx.serialization.Serializable

@Serializable
data class CanvasMemoRequest(
    val title: String = "",
    val type: String = "CANVAS",
    val startPage: Int = 0,
    val endPage: Int = 0
)

fun CanvasMemoFormUiModel.toRequest() = CanvasMemoRequest(
    title = title,
    type = "CANVAS",
    startPage = startPage,
    endPage = endPage
)