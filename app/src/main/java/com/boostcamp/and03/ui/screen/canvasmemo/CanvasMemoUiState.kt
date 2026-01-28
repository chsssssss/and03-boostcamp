package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CanvasMemoUiState(
    val nodes: Map<String, MemoNodeUiModel> = emptyMap(),
    val edges: List<EdgeUiModel> = emptyList(),

    val relationSelection: RelationSelection? = null,
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

    val characters: ImmutableList<CharacterUiModel> = persistentListOf(),
    val quotes: ImmutableList<QuoteUiModel> = persistentListOf(),
    val totalPage: Int = 0,

    val isSaving: Boolean = false,
    val isBottomBarVisible: Boolean = true
) {
    val isQuoteSaveable: Boolean
        get() = pageState.text.toString().toIntOrNull() in 1..totalPage && quoteState.text.isNotBlank()
}

data class RelationSelection(
    val fromNodeId: String?,
    val toNodeId: String?
) {
    val isComplete: Boolean
        get() = fromNodeId != null && toNodeId != null
}