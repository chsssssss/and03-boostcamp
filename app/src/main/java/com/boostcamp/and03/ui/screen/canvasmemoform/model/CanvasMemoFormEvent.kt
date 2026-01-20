package com.boostcamp.and03.ui.screen.canvasmemoform.model

sealed interface CanvasMemoFormEvent {

    data object NavigateBack : CanvasMemoFormEvent
}