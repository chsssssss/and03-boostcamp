package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.domain.model.MemoNode
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel

data class CanvasMemoUiState(
    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val edges: List<EdgeUiModel> = emptyList(),
    val relationSelection: RelationSelection? = null,
    val relationNameState: TextFieldState = TextFieldState(),
    val isRelationDialogVisible: Boolean = false,
    val isAddCharacterDialogVisible: Boolean = false,
    val characterNameState: TextFieldState = TextFieldState(),
    val characterDescState: TextFieldState = TextFieldState(),
) {
    val fromCharacterName: String
        get() = relationSelection?.fromNodeId?.let { id ->
            (nodes[id]?.node as? MemoNode.CharacterNode)?.name
        } ?: ""

    val toCharacterName: String
        get() = relationSelection?.toNodeId?.let { id ->
            (nodes[id]?.node as? MemoNode.CharacterNode)?.name
        } ?: ""
}

data class RelationSelection(
    val fromNodeId: String?,
    val toNodeId: String?
) {
    val isComplete: Boolean
        get() = fromNodeId != null && toNodeId != null
}