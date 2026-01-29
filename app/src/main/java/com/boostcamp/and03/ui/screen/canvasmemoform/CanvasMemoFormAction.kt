package com.boostcamp.and03.ui.screen.canvasmemoform

sealed interface CanvasMemoFormAction {

    data object OnBackClick : CanvasMemoFormAction

    data object OnSaveClick : CanvasMemoFormAction

    data class OnTitleChange(val title: String) : CanvasMemoFormAction

    data class OnStartPageChange(val startPage: String) : CanvasMemoFormAction

    data class OnEndPageChange(val endPage: String) : CanvasMemoFormAction

    data object CloseExitConfirmationDialog : CanvasMemoFormAction

    data object CloseScreen : CanvasMemoFormAction
}