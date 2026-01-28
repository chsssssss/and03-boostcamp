package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel

data class CanvasMemoUiState(
    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val edges: List<EdgeUiModel> = emptyList(),
    val relationSelection: RelationSelection? = null,
    val relationNameState: TextFieldState = TextFieldState(),
    val isRelationDialogVisible: Boolean = false,
    val isAddCharacterDialogVisible: Boolean = false,
    val characterNameState: TextFieldState = TextFieldState(),
    val characterDescState: TextFieldState = TextFieldState(),

    val isAddNodeSheetVisible: Boolean = false,

    val characters: List<CharacterUiModel> = emptyList(),

    val selectedBottomBarType: MainBottomBarType = MainBottomBarType.NODE
)

data class RelationSelection(
    val fromNodeId: String?,
    val toNodeId: String?
) {
    val isComplete: Boolean
        get() = fromNodeId != null && toNodeId != null
}
