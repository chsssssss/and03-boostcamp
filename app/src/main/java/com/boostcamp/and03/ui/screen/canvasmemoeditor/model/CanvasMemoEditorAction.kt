package com.boostcamp.and03.ui.screen.canvasmemoeditor.model

sealed interface CanvasMemoEditorAction {

    data object OnBackClick : CanvasMemoEditorAction

    data object OnSaveClick : CanvasMemoEditorAction

    data class OnTitleChange(val title: String) : CanvasMemoEditorAction

    data class OnStartPageChange(val startPage: String) : CanvasMemoEditorAction

    data class OnEndPageChange(val endPage: String) : CanvasMemoEditorAction
}