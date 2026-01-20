package com.boostcamp.and03.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResultItem(
    @SerialName("title") val title: String,
    @SerialName("image") val thumbnail: String,
    @SerialName("author") val author: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("isbn") val isbn: String
)