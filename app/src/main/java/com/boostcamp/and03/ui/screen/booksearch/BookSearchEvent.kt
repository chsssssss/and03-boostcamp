package com.boostcamp.and03.ui.screen.booksearch

sealed interface BookSearchEvent {

    data object NavigateBack : BookSearchEvent

    data object NavigateToManualAdd : BookSearchEvent
}