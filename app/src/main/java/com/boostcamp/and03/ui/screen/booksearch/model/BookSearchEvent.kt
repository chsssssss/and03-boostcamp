package com.boostcamp.and03.ui.screen.booksearch.model

sealed interface BookSearchEvent {

    data object NavigateBack : BookSearchEvent

    data object NavigateToManualAdd : BookSearchEvent

    data class SaveFailure(val reason: SaveFailureReason) : BookSearchEvent
}