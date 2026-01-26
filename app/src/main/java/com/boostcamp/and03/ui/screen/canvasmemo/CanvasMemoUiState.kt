package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
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
    val selectedBottomBarType: MainBottomBarType = MainBottomBarType.NODE, // 하단 메인 바텀바 상태 기본값은 노드로 설정함
    val isBottomBarVisible: Boolean = true,
)

data class RelationSelection(
    val fromNodeId: String?,
    val toNodeId: String?
) {
    val isComplete: Boolean
        get() = fromNodeId != null && toNodeId != null
}