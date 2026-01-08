package com.boostcamp.and03.ui.screen.booksearch.model

sealed interface BookSearchEvent {

    data object NavigateBack : BookSearchEvent

    data object SaveSuccess : BookSearchEvent

    data class SaveFailure(val message: String) : BookSearchEvent
}