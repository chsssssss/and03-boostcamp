package com.boostcamp.and03.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookLookUpResponse(
    @SerialName("item") val item: List<BookInfoItem>
)

@Serializable
data class BookInfoItem(
    @SerialName("bookinfo") val bookInfo: PageInfo
)

@Serializable
data class PageInfo(
    @SerialName("itemPage") val itemPage: Int
)