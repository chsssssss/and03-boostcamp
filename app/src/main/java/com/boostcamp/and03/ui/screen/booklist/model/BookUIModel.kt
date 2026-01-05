package com.boostcamp.and03.ui.screen.booklist.model

import kotlinx.collections.immutable.ImmutableList
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