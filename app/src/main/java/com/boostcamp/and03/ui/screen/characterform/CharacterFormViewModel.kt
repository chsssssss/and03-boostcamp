package com.boostcamp.and03.ui.screen.characterform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterFormViewModel @Inject constructor (
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val characterFormRoute = savedStateHandle.toRoute<Route.CharacterForm>()
    private val bookId = characterFormRoute.bookId
    private val characterId = characterFormRoute.characterId

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

    private val _uiState = MutableStateFlow(CharacterFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<CharacterFormEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: CharacterFormAction) {
        when (action) {
            CharacterFormAction.OnBackClick -> _event.trySend(CharacterFormEvent.NavigateBack)

            CharacterFormAction.OnSaveClick -> {
                viewModelScope.launch {
                    try {
                        saveCharacter()
                        _event.trySend(CharacterFormEvent.NavigateBack)
                    } catch (e: Exception) {
                        // TODO: 오류 메시지 UI 표시 구현
                    }
                }
            }

            CharacterFormAction.OnAddImageClick -> { /* TODO: 등장인물 사진 추가 동작 구현 */ }

            is CharacterFormAction.OnNameChange -> _uiState.update { it.copy(name = action.name) }

            is CharacterFormAction.OnRoleChange -> _uiState.update { it.copy(role = action.role) }

            is CharacterFormAction.OnDescriptionChange -> _uiState.update { it.copy(description = action.description) }
        }
    }

    init {
        viewModelScope.launch {
            loadCharacter()
        }
    }

    private suspend fun loadCharacter() {
        if (characterId.isBlank()) return

        val result = bookStorageRepository.getCharacter(
            userId = userId,
            bookId = bookId,
            characterId = characterId
        )

        _uiState.update {
            it.copy(
                name = result.name,
                role = result.role,
                description = result.description
            )
        }
    }

    private suspend fun saveCharacter() {
        if (characterId.isBlank()) {
            bookStorageRepository.addCharacter(
                userId = userId,
                bookId = bookId,
                character = _uiState.value.toUiModel()
            )
        } else {
            bookStorageRepository.updateCharacter(
                userId = userId,
                bookId = bookId,
                characterId = characterId,
                character = _uiState.value.toUiModel()
            )
        }
    }
}