package com.boostcamp.and03.ui.screen.characterform

import androidx.compose.ui.graphics.Color

data class CharacterFormUiState(
    val name: String = "",
    val role: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val iconColor: Color = Color(0xFF1E88E5),
    val isSaving: Boolean = false,
    val isExitConfirmationDialogVisible: Boolean = false
) {
    val isSaveable: Boolean
        get() = name.isNotBlank()

    val isEdited: Boolean
        get() = name.isNotBlank() ||
                role.isNotBlank() ||
                description.isNotBlank() ||
                imageUrl.isNotBlank()
}