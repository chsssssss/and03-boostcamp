package com.boostcamp.and03.ui.screen.bookdetail.model

import androidx.compose.ui.graphics.Color
import com.boostcamp.and03.data.model.response.CharacterResponse

data class CharacterUiModel(
    val id: String,
    val name: String,
    val role: String,
    val iconColor: Color,
    val description: String
)

fun CharacterResponse.toUiModel(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        role = role,
        description = description,
        iconColor = Color(0xFF1E88E5)
    )
}
