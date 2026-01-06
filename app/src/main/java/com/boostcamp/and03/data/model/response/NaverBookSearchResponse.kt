package com.boostcamp.and03.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NaverBookSearchResponse(
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverBookItem>
) {
    val hasNext: Boolean
        get() = start + display <= total

    val nextStart: Int
        get() = start + display
}