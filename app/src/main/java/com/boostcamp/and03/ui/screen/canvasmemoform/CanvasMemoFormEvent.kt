package com.boostcamp.and03.ui.screen.canvasmemoform

sealed interface CanvasMemoFormEvent {

    data object NavigateBack : CanvasMemoFormEvent

    data object NavigateCanvasMemo : CanvasMemoFormEvent

}