package com.boostcamp.and03.ui.screen.quoteform

data class QuoteFormUiState(
    val quote: String = "",
    val page: String = "",
    val isSaving: Boolean = false
) {
    val isSaveable: Boolean
        get() = quote.isNotBlank() && page.isNotBlank()
}