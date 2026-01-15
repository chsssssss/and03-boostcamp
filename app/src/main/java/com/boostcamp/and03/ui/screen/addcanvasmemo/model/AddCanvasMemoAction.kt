package com.boostcamp.and03.ui.screen.addcanvasmemo.model

sealed interface AddCanvasMemoAction {

    data object OnBackClick : AddCanvasMemoAction

    data object OnSaveClick : AddCanvasMemoAction

    data class OnTitleChange(val title: String) : AddCanvasMemoAction

    data class OnStartPageChange(val startPage: String) : AddCanvasMemoAction

    data class OnEndPageChange(val endPage: String) : AddCanvasMemoAction
}