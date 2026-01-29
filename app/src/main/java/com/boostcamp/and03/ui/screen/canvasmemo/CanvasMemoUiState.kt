package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationAddStep
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationDialogUiState
import com.boostcamp.and03.ui.screen.canvasmemo.model.toRelationDialogState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CanvasMemoUiState(
    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val edges: List<EdgeUiModel> = emptyList(),

    val zoomScale: Float = 1f,
    val canvasViewOffset: Offset = Offset.Zero, // 손가락으로 화면을 드래그했을 때 캔버스가 이동한 거리

    val relationSelection: RelationSelection = RelationSelection.empty(),
    val relationNameState: TextFieldState = TextFieldState(),

    val bottomSheetType: CanvasMemoBottomSheetType? = null,

    val isAddingCharacter: Boolean = false,
    val isAddingQuote: Boolean = false,

    val isAddCharacterDialogVisible: Boolean = false,
    val isRelationDialogVisible: Boolean = false,
    val isQuoteDialogVisible: Boolean = false,

    val characterNameState: TextFieldState = TextFieldState(),
    val characterDescState: TextFieldState = TextFieldState(),

    val quoteState: TextFieldState = TextFieldState(),
    val pageState: TextFieldState = TextFieldState(),

    val selectedBottomBarType: MainBottomBarType = MainBottomBarType.NODE, // 하단 메인 바텀바 상태 기본값은 노드로 설정함
    val isBottomBarVisible: Boolean = true,
    val relationAddStep: RelationAddStep = RelationAddStep.NONE,

    val characters: ImmutableList<CharacterUiModel> = persistentListOf(),
    val quotes: ImmutableList<QuoteUiModel> = persistentListOf(),
    val totalPage: Int = 0,

    val isSaving: Boolean = false,
    val quoteToPlace: QuoteUiModel? = null,
    val quoteItemSizeDp: IntSize? = null
) {
    val relationDialogUiState: RelationDialogUiState
        get() = toRelationDialogState()

    val isQuoteSaveable: Boolean
        get() = pageState.text.toString().toIntOrNull() in 1..totalPage && quoteState.text.isNotBlank()
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
