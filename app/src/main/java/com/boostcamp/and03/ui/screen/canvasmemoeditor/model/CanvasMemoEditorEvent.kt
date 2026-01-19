package com.boostcamp.and03.ui.screen.canvasmemoeditor.model

sealed interface CanvasMemoEditorEvent {

    data object NavigateBack : CanvasMemoEditorEvent
}