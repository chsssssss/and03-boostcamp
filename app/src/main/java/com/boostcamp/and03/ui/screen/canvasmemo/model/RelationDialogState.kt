package com.boostcamp.and03.ui.screen.canvasmemo.model

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.ui.screen.canvasmemo.CanvasMemoUiState
import com.boostcamp.and03.ui.util.getCharacterImage
import com.boostcamp.and03.ui.util.getCharacterName

data class RelationDialogUiState(
    val relationNameState: TextFieldState = TextFieldState(""),
    val fromNodeId: String = "",
    val toNodeId: String = "",
    val fromName: String = "",
    val toName: String = "",
    val fromImageUrl: String = "",
    val toImageUrl: String = ""
)

fun CanvasMemoUiState.toRelationDialogState(): RelationDialogUiState {
    val fromId = relationSelection.fromNodeId
    val toId = relationSelection.toNodeId

    if (fromId == null || toId == null) return RelationDialogUiState()

    return RelationDialogUiState(
        relationNameState = relationNameState,
        fromName = nodes.getCharacterName(fromId) ?: "",
        toName = nodes.getCharacterName(toId) ?: "",
        fromImageUrl = nodes.getCharacterImage(fromId) ?: "",
        toImageUrl = nodes.getCharacterImage(toId) ?: "",
        fromNodeId = fromId,
        toNodeId = toId
    )
}