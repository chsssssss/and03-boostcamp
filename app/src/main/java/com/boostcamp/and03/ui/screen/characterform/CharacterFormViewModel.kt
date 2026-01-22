package com.boostcamp.and03.ui.screen.characterform

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _uiState = MutableStateFlow(CharacterFormUiState())
    val uiState = _uiState.asStateFlow()

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

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
                role = result.role
            )
        }
    }

    fun changeName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun changeRole(role: String) {
        _uiState.update { it.copy(role = role) }
    }

    fun changeDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun changeIconColor(color: Color) {
        _uiState.update { it.copy(iconColor = color) }
    }

    fun changeImageUrl(url: String) {
        _uiState.update { it.copy(imageUrl = url) }
    }

    suspend fun saveCharacter() {
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