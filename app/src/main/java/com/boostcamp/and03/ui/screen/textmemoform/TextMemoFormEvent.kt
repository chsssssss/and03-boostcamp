package com.boostcamp.and03.ui.screen.textmemoform

sealed interface TextMemoFormEvent {

    data object NavigateBack : TextMemoFormEvent
}