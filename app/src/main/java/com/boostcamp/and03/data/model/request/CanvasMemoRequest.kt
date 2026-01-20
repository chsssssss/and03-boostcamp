package com.boostcamp.and03.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CanvasMemoRequest(
    val title: String = "",
    val type: String = "CANVAS",
    val startPage: Int = 0,
    val endPage: Int = 0
)