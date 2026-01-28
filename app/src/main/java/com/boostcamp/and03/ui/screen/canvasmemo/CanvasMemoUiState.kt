package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationAddStep
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationDialogUiState
import com.boostcamp.and03.ui.screen.canvasmemo.model.toRelationDialogState

data class CanvasMemoUiState(
    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val edges: List<EdgeUiModel> = emptyList(),
    val relationSelection: RelationSelection = RelationSelection.empty(),
    val relationNameState: TextFieldState = TextFieldState(),
    val isRelationDialogVisible: Boolean = false,
    val isAddCharacterDialogVisible: Boolean = false,
    val characterNameState: TextFieldState = TextFieldState(),
    val characterDescState: TextFieldState = TextFieldState(),
    val selectedBottomBarType: MainBottomBarType = MainBottomBarType.NODE, // 하단 메인 바텀바 상태 기본값은 노드로 설정함
    val isBottomBarVisible: Boolean = true,
    val relationAddStep: RelationAddStep = RelationAddStep.NONE,
) {
    val relationDialogUiState: RelationDialogUiState
        get() = toRelationDialogState()

//    val relationAddStep: RelationAddStep
//        get() = when {
//            relationSelection.fromNodeId == null && relationSelection.toNodeId == null ->
//                RelationAddStep.NONE
//
//            relationSelection.fromNodeId != null && relationSelection.toNodeId == null ->
//                RelationAddStep.FROM_ONLY
//
//            relationSelection.fromNodeId != null && relationSelection.toNodeId != null ->
//                RelationAddStep.COMPLETE
//        }
}

data class RelationSelection(
    val fromNodeId: String?,
    val toNodeId: String?
) {
    val isComplete: Boolean
        get() = fromNodeId != null && toNodeId != null

    companion object {
        fun empty(): RelationSelection =
            RelationSelection(
                fromNodeId = null,
                toNodeId = null
            )
    }
}