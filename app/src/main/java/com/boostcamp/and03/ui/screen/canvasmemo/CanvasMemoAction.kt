package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.ui.geometry.Offset

sealed class CanvasMemoAction {
    data object ClickBack : CanvasMemoAction()
    data object CloseRelationDialog : CanvasMemoAction()
    data object CloseAddCharacterDialog : CanvasMemoAction()
    data class OpenRelationDialog(
        val fromNodeId: String,
        val toNodeId: String
    ) : CanvasMemoAction()

    data class MoveNode(val nodeId: String, val newOffset: Offset) : CanvasMemoAction()
    data class ConnectNodes(val fromId: String, val toId: String, val name: String) : CanvasMemoAction()

}