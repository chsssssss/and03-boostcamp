package com.boostcamp.and03.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class QuoteRequest(
    val content: String = "",
    val page: Int = 0,
)