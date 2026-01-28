package com.boostcamp.and03.ui.screen.canvasmemo

import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType

import androidx.compose.ui.geometry.Offset

sealed class CanvasMemoAction {
    data object ClickBack : CanvasMemoAction()
    data object CloseRelationDialog : CanvasMemoAction()
    data object CloseAddCharacterDialog : CanvasMemoAction()
    data class OnBottomBarClick(
        val type: MainBottomBarType
    ) : CanvasMemoAction()

    data class MoveNode(val nodeId: String, val newOffset: Offset) : CanvasMemoAction()
    object HideBottomBar : CanvasMemoAction()

    object ShowBottomBar : CanvasMemoAction()

    data class OnNodeClick(val nodeId: String) : CanvasMemoAction()

    data class ConfirmRelation(val fromId: String, val toId: String, val name: String) : CanvasMemoAction()

}