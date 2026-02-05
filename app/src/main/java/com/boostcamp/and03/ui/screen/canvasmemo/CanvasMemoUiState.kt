package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.data.model.request.ProfileType
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
    val isLoading: Boolean = true,

    val title: String = "",
    val startPage: Int = 1,
    val endPage: Int = 1,

    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val edges: List<EdgeUiModel> = emptyList(),

    val zoomScale: Float = 0.6f,
    val canvasViewOffset: Offset = Offset.Zero, // 손가락으로 화면을 드래그했을 때 캔버스가 이동한 거리

    val relationSelection: RelationSelection = RelationSelection.empty(),
    val relationNameState: TextFieldState = TextFieldState(),

    val bottomSheetType: CanvasMemoBottomSheetType? = null,

    val isAddingCharacter: Boolean = false,
    val isAddingQuote: Boolean = false,

    val isAddCharacterDialogVisible: Boolean = false,
    val isRelationDialogVisible: Boolean = false,
    val isQuoteDialogVisible: Boolean = false,
    val isExitConfirmationDialogVisible: Boolean = false,
    val isSureDeleteDialogVisible: Boolean = false,

    val characterProfileType: ProfileType = ProfileType.COLOR,
    val characterImageUrl: String? = null,
    val characterIconColor: Color = Color(0xFF1E88E5), // 임시 컬러 적용
    val isCharacterSaveable: Boolean = true,

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
    val quoteItemSizePx: IntSize? = null,
    val nodeToPlace: CharacterUiModel? = null,
    val nodeItemSizePx: IntSize? = null,

    val hasUnsavedChanges: Boolean = false,

    val isDeleteMode: Boolean = false,
    val selectedDeleteItemIds: ImmutableList<String> = persistentListOf(),
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
