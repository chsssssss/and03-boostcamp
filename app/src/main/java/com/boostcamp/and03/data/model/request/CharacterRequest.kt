package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import kotlinx.serialization.Serializable

@Serializable
data class CharacterRequest(
    val role: String = "",
    val description: String = "",
    val name: String = ""
)

fun CharacterUiModel.toRequest() = CharacterRequest(
    role = role,
    description = description,
    name = name
)