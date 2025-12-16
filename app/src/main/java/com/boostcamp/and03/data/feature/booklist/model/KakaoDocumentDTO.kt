package com.boostcamp.and03.data.feature.booklist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoDocumentDTO(
    @SerialName("title") val title: String,
    @SerialName("authors") val authors: List<String>,
    @SerialName("publisher") val publisher: String,
    @SerialName("thumbnail") val thumbnail: String?
)