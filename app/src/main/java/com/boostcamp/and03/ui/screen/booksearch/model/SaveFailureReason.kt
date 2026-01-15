package com.boostcamp.and03.ui.screen.booksearch.model

sealed interface SaveFailureReason {

    data object Network : SaveFailureReason

    data object Permission : SaveFailureReason

    data object Unknown : SaveFailureReason
}