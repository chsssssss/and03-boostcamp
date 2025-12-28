package com.boostcamp.and03.ui.screen.booklist.model

import com.boostcamp.and03.data.feature.booklist.model.KakaoDocumentDTO
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class BookUIModel(
    val title: String,
    val authors: ImmutableList<String>,
    val publisher: String,
    val thumbnail: String
) {
    val hasThumbnail: Boolean
        get() = thumbnail.isNotEmpty()
}

fun KakaoDocumentDTO.toUiModel() = BookUIModel(
    title = title,
    authors = authors.toImmutableList(),
    publisher = publisher,
    thumbnail = thumbnail
)