package com.boostcamp.and03.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AladinBookInfo(
    @SerialName("itemPage") val itemPage: Int
)