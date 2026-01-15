package com.boostcamp.and03.ui.screen.addcanvasmemo.model

sealed interface AddCanvasMemoEvent {

    data object NavigateBack : AddCanvasMemoEvent
}