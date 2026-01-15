package com.boostcamp.and03.ui.screen.addtextmemo.model

sealed interface AddTextMemoAction {

    data object OnBackClick : AddTextMemoAction

    data object OnSaveClick : AddTextMemoAction

    data class OnTitleChange(val title: String) : AddTextMemoAction

    data class OnContentChange(val content: String) : AddTextMemoAction

    data class OnStartPageChange(val startPage: String) : AddTextMemoAction

    data class OnEndPageChange(val endPage: String) : AddTextMemoAction
}