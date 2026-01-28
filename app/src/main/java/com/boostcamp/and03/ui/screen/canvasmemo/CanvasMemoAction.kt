package com.boostcamp.and03.ui.screen.canvasmemo

import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType

import androidx.compose.ui.geometry.Offset
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel

sealed interface CanvasMemoAction {

    data object ClickBack : CanvasMemoAction

    data object CloseBottomSheet : CanvasMemoAction

    data object CloseRelationDialog : CanvasMemoAction

    data object CloseAddCharacterDialog : CanvasMemoAction

    data object CloseQuoteDialog : CanvasMemoAction

    data class PrepareQuotePlacement(val quote: QuoteUiModel) : CanvasMemoAction

    data object SaveQuote : CanvasMemoAction

    data class SearchQuote(val query: String) : CanvasMemoAction

    data object AddNewQuote : CanvasMemoAction

    data class OpenRelationDialog(
        val fromNodeId: String,
        val toNodeId: String
    ) : CanvasMemoAction

    data class OnBottomBarClick(val type: MainBottomBarType) : CanvasMemoAction

    data class MoveNode(
        val nodeId: String,
        val newOffset: Offset
    ) : CanvasMemoAction

    data class ConnectNodes(
        val fromId: String,
        val toId: String,
        val name: String
    ) : CanvasMemoAction

    data object CancelPlaceItem : CanvasMemoAction

    data class TapCanvas(val tapPositionOnScreen: Offset) : CanvasMemoAction
}