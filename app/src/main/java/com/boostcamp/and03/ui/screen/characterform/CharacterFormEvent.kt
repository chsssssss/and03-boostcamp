package com.boostcamp.and03.ui.screen.characterform

sealed interface CharacterFormEvent {

    data object NavigateBack: CharacterFormEvent
}