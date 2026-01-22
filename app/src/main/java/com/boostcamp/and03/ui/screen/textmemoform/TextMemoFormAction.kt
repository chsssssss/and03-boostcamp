package com.boostcamp.and03.ui.screen.textmemoform

sealed interface TextMemoFormAction {

    data object OnBackClick : TextMemoFormAction

    data object OnSaveClick : TextMemoFormAction

    data class OnTitleChange(val title: String) : TextMemoFormAction

    data class OnContentChange(val content: String) : TextMemoFormAction

    data class OnStartPageChange(val startPage: String) : TextMemoFormAction

    data class OnEndPageChange(val endPage: String) : TextMemoFormAction
}