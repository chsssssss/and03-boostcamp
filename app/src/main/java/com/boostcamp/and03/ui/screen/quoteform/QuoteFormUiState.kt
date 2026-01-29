package com.boostcamp.and03.ui.screen.quoteform

data class QuoteFormUiState(
    val quote: String = "",
    val page: String = "",

    val originalQuote: String = "",
    val originalPage: String = "",

    val isLoading: Boolean = false,
    val totalPage: Int = 0,
    val isSaving: Boolean = false,
    val isExitConfirmationDialogVisible: Boolean = false
) {
    val isValidPage: Boolean
        get() = page.trim().toIntOrNull() in 1..totalPage

    val isSaveable: Boolean
        get() = quote.isNotBlank() && isValidPage

    val isEdited: Boolean
        get() = quote != originalQuote || page != originalPage
}