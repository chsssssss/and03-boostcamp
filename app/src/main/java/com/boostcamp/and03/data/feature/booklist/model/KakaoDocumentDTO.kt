package com.boostcamp.and03.data.feature.booklist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoDocumentDTO(
    @SerialName("title") val bookTitle: String,
    @SerialName("authors") val bookAuthors: List<String>,
    @SerialName("publisher") val bookPublisher: String,
    @SerialName("thumbnail") val bookThumbnailURL: String
)