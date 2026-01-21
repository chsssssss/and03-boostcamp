package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel

data class CanvasMemoUiState(
    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val relationSelection: RelationSelection? = null,
    val relationNameState: TextFieldState = TextFieldState(),
    val isRelationDialogVisible: Boolean = false,
    val isAddCharacterDialogVisible: Boolean = false,
    val characterNameState: TextFieldState = TextFieldState(),
    val characterDescState: TextFieldState = TextFieldState(),
)

data class RelationSelection(
    val fromNodeId: String?,
    val toNodeId: String?
) {
    val isComplete: Boolean
        get() = fromNodeId != null && toNodeId != null
}