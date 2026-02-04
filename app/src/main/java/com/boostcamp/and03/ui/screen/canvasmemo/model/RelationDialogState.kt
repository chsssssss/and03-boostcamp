package com.boostcamp.and03.ui.screen.canvasmemo.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.graphics.Color
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.ui.screen.canvasmemo.CanvasMemoUiState
import com.boostcamp.and03.ui.util.getCharacterImage
import com.boostcamp.and03.ui.util.getCharacterName
import com.boostcamp.and03.ui.util.getIconColor
import com.boostcamp.and03.ui.util.getProfileType

data class RelationDialogUiState(
    val relationNameState: TextFieldState = TextFieldState(""),
    val fromNodeId: String = "",
    val toNodeId: String = "",
    val fromProfileType: ProfileType = ProfileType.COLOR,
    val toProfileType: ProfileType = ProfileType.COLOR,
    val fromName: String = "",
    val toName: String = "",
    val fromImageUrl: String = "",
    val toImageUrl: String = "",
    val fromIconColor: Color = Color.Unspecified,
    val toIconColor: Color = Color.Unspecified
)

fun CanvasMemoUiState.toRelationDialogState(): RelationDialogUiState {
    val fromId = relationSelection.fromNodeId
    val toId = relationSelection.toNodeId

    if (fromId == null || toId == null) return RelationDialogUiState()

    return RelationDialogUiState(
        relationNameState = relationNameState,
        fromName = nodes.getCharacterName(fromId) ?: "",
        toName = nodes.getCharacterName(toId) ?: "",
        fromProfileType = nodes.getProfileType(fromId) ?: ProfileType.COLOR,
        toProfileType = nodes.getProfileType(toId) ?: ProfileType.COLOR,
        fromImageUrl = nodes.getCharacterImage(fromId) ?: "",
        toImageUrl = nodes.getCharacterImage(toId) ?: "",
        fromIconColor = nodes.getIconColor(fromId) ?: Color.Unspecified,
        toIconColor = nodes.getIconColor(toId) ?: Color.Unspecified,
        fromNodeId = fromId,
        toNodeId = toId
    )
}