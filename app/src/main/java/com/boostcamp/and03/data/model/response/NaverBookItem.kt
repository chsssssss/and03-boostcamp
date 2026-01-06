package com.boostcamp.and03.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NaverBookItem(
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
)