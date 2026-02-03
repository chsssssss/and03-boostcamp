package com.boostcamp.and03.ui.screen.characterform

import androidx.compose.ui.graphics.Color
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.ui.util.random

data class CharacterFormUiState(
    val name: String = "",
    val role: String = "",
    val description: String = "",
    val profileType: ProfileType = ProfileType.COLOR,
    val imageUrl: String = "",
    val profileColor: Color = Color.random(),

    val originalName: String = "",
    val originalRole: String = "",
    val originalDescription: String = "",
    val originalImageUrl: String = "",

    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isVisibleBottomSheet: Boolean = false,
    val isExitConfirmationDialogVisible: Boolean = false
) {
    val isSaveable: Boolean
        get() = name.isNotBlank()

    val isEdited: Boolean
        get() = name != originalName ||
                role != originalRole ||
                description != originalDescription ||
                imageUrl != originalImageUrl
}