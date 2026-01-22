package com.boostcamp.and03.ui.screen.quoteform

sealed interface QuoteFormAction {

    data object OnBackClick: QuoteFormAction

    data object OnSaveClick: QuoteFormAction

    data class OnContentChange(val content: String): QuoteFormAction

    data class OnPageChange(val page: String): QuoteFormAction
}