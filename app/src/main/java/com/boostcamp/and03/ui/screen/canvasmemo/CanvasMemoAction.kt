package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import kotlinx.collections.immutable.ImmutableList


sealed interface CanvasMemoAction {

    data object ClickBack : CanvasMemoAction

    data object CloseRelationDialog : CanvasMemoAction

    data object CloseAddCharacterDialog : CanvasMemoAction

    data object CloseBottomSheet : CanvasMemoAction

    data object CloseQuoteDialog : CanvasMemoAction

    data object CloseExitConfirmationDialog : CanvasMemoAction

    data object CloseSureDeleteDialog : CanvasMemoAction

    data object CloseScreen : CanvasMemoAction

    data class PrepareQuotePlacement(val quote: QuoteUiModel) : CanvasMemoAction

    data object SaveQuote : CanvasMemoAction

    data class SearchQuote(val query: String) : CanvasMemoAction

    data object AddNewQuote : CanvasMemoAction

    data class OpenRelationDialog(
        val fromNodeId: String,
        val toNodeId: String
    ) : CanvasMemoAction

    data class MoveNode(
        val nodeId: String,
        val newOffset: Offset
    ) : CanvasMemoAction

    data object HideBottomBar : CanvasMemoAction

    data object ShowBottomBar : CanvasMemoAction

    data class OnNodeClick(val nodeId: String) : CanvasMemoAction

    data class ConfirmRelation(
        val fromId: String,
        val toId: String,
        val name: String
    ) : CanvasMemoAction

    data object OnClickSave : CanvasMemoAction

    data class OnBottomBarClick(val type: MainBottomBarType) : CanvasMemoAction

    data object CancelPlaceItem : CanvasMemoAction

    data class TapCanvas(val tapPositionOnScreen: Offset) : CanvasMemoAction

    data class UpdateQuoteItemSize(val size: IntSize) : CanvasMemoAction

    data class ZoomCanvasByGesture(
        val centroid: Offset,
        val moveOffset: Offset,
        val zoomChange: Float
    ) : CanvasMemoAction

    data object ZoomIn : CanvasMemoAction

    data object ZoomOut : CanvasMemoAction

    data object ResetZoom : CanvasMemoAction

    data class PrepareNodePlacement(val character: CharacterUiModel) : CanvasMemoAction

    data class AddNodeAtPosition(val positionOnScreen: Offset) : CanvasMemoAction

    data class SelectDeleteItem(val itemId: String) : CanvasMemoAction

    data object CancelDeleteMode : CanvasMemoAction

    data object OpenSureDeleteDialog : CanvasMemoAction

    data class DeleteSelectedItems(val itemIds: ImmutableList<String>) : CanvasMemoAction
}
