package com.boostcamp.and03.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NaverBookSearchResponse(
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverBookSearchItem>
) {
    val hasNext: Boolean
        get() = start + display <= total

    val nextStart: Int
        get() = start + display
}

@Serializable
data class NaverBookSearchItem(
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
)