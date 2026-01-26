package com.boostcamp.and03.ui.screen.booksearch

import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchResultUiModel

sealed interface BookSearchAction {

    data object OnBackClick : BookSearchAction

    data object OnManualAddClick : BookSearchAction

    data class OnItemClick(val index: Int) : BookSearchAction

    data class OnSaveClick(val selectedItem: BookSearchResultUiModel) : BookSearchAction

    data class OnQueryChange(val query: String) : BookSearchAction
}