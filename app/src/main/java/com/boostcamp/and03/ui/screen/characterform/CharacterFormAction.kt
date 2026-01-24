package com.boostcamp.and03.ui.screen.characterform

sealed interface CharacterFormAction {

    data object OnBackClick: CharacterFormAction

    data object OnSaveClick: CharacterFormAction

    data object OnAddImageClick: CharacterFormAction

    data class OnNameChange(val name: String): CharacterFormAction

    data class OnRoleChange(val role: String): CharacterFormAction

    data class OnDescriptionChange(val description: String): CharacterFormAction
}