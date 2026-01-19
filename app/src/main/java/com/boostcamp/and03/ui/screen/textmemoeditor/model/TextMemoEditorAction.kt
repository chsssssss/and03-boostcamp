package com.boostcamp.and03.ui.screen.textmemoeditor.model

sealed interface TextMemoEditorAction {

    data object OnBackClick : TextMemoEditorAction

    data object OnSaveClick : TextMemoEditorAction

    data class OnTitleChange(val title: String) : TextMemoEditorAction

    data class OnContentChange(val content: String) : TextMemoEditorAction

    data class OnStartPageChange(val startPage: String) : TextMemoEditorAction

    data class OnEndPageChange(val endPage: String) : TextMemoEditorAction
}