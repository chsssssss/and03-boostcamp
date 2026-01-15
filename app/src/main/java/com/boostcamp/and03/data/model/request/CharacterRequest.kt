package com.boostcamp.and03.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CharacterRequest(
    val role: String = "",
    val description: String = "",
    val name: String = ""
)