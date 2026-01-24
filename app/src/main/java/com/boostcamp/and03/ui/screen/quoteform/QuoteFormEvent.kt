package com.boostcamp.and03.ui.screen.quoteform

sealed interface QuoteFormEvent {

    data object NavigateBack : QuoteFormEvent
}