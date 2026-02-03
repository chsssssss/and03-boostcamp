package com.boostcamp.and03.ui.screen.bookdetail.model

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.ui.screen.characterform.CharacterFormUiState
import androidx.core.graphics.toColorInt
import com.boostcamp.and03.ui.util.random
import com.boostcamp.and03.ui.util.toColorOrNull

data class CharacterUiModel(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val profileType: ProfileType = ProfileType.COLOR,
    val profileColor: Color? = null,
    val imageUri: String? = null,
    val description: String = ""
)

fun CharacterResponse.toUiModel(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        role = role,
        description = description,
        profileType = if (profileType == "IMAGE") ProfileType.IMAGE else ProfileType.COLOR,
        imageUri = profileImgUri,
        profileColor = if (profileType == "COLOR") profileColor?.toColorOrNull() else null
    )
}

fun CharacterFormUiState.toUiModel(): CharacterUiModel {
    return CharacterUiModel(
        name = name,
        role = role,
        description = description,
    )
}

fun CharacterFormUiState.toRequest(): CharacterRequest {
    val isImage = imageUrl.isNotBlank()

    return CharacterRequest(
        name = name,
        role = role,
        description = description,
        profileType = if (isImage) ProfileType.IMAGE else ProfileType.COLOR,
        profileImgUri = if (isImage) imageUrl else null,
        profileColor = if (!isImage) profileColor.value.toHexString() else null
    )
}
