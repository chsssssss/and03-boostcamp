package com.boostcamp.and03.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AladinBookLookUpResponse(
    val itemPage: Int
)