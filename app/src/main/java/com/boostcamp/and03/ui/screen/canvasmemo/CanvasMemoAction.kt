package com.boostcamp.and03.ui.screen.canvasmemo

import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType

sealed class CanvasMemoAction {
    data object ClickBack : CanvasMemoAction()
    data object CloseRelationDialog : CanvasMemoAction()
    data object CloseAddCharacterDialog : CanvasMemoAction()
    data class OpenRelationDialog(
        val fromNodeId: String,
        val toNodeId: String
    ) : CanvasMemoAction()
    data class OnBottomBarClick(
        val type: MainBottomBarType
    ) : CanvasMemoAction()
}