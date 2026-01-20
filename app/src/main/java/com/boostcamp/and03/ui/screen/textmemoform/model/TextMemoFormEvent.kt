package com.boostcamp.and03.ui.screen.textmemoform.model

sealed interface TextMemoFormEvent {

    data object NavigateBack : TextMemoFormEvent
}