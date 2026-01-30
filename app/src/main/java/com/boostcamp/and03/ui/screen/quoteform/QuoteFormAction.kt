package com.boostcamp.and03.ui.screen.quoteform

sealed interface QuoteFormAction {

    data object OnBackClick: QuoteFormAction

    data object OnSaveClick: QuoteFormAction

    data class OnQuoteChange(val quote: String): QuoteFormAction

    data class OnPageChange(val page: String): QuoteFormAction

    data object CloseScreen: QuoteFormAction

    data object CloseExitConfirmationDialog: QuoteFormAction
}