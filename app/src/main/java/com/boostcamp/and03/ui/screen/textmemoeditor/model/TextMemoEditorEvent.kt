package com.boostcamp.and03.ui.screen.textmemoeditor.model

sealed interface TextMemoEditorEvent {

    data object NavigateBack : TextMemoEditorEvent
}