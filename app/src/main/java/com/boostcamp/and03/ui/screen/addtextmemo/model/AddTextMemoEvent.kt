package com.boostcamp.and03.ui.screen.addtextmemo.model

sealed interface AddTextMemoEvent {

    data object NavigateBack : AddTextMemoEvent

    data object SaveTextMemo : AddTextMemoEvent
}