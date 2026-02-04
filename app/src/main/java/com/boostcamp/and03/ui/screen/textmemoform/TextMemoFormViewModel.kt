package com.boostcamp.and03.ui.screen.textmemoform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.canvasmemoform.CanvasMemoFormEvent
import com.boostcamp.and03.ui.screen.textmemoform.model.toUiModel
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
class TextMemoFormViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val textMemoFormRoute = savedStateHandle.toRoute<Route.TextMemoForm>()
    private val bookId = textMemoFormRoute.bookId
    private val memoId = textMemoFormRoute.memoId
    private val totalPage = textMemoFormRoute.totalPage

    private val _uiState = MutableStateFlow(TextMemoFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<TextMemoFormEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

    init {
        _uiState.update {
            it.copy(
                totalPage = totalPage,
                isLoading = memoId.isNotBlank()
            )
        }

        if (memoId.isNotBlank()) {
            viewModelScope.launch { loadTextMemo() }
        }
    }

    fun onAction(action: TextMemoFormAction) {
        when (action) {
            TextMemoFormAction.OnBackClick -> {
                if (_uiState.value.isEdited) {
                    _uiState.update { it.copy(isExitConfirmationDialogVisible = true) }
                } else {
                    _event.trySend(TextMemoFormEvent.NavigateBack)
                }
            }

            TextMemoFormAction.OnSaveClick -> {
                viewModelScope.launch {
                    if (_uiState.value.isSaving) return@launch

                    _uiState.update { it.copy(isSaving = true) }

                    try {
                        saveTextMemo()
                        _event.send(TextMemoFormEvent.NavigateBack)
                    } catch (e: Exception) {
                        // TODO: 오류 메시지 UI 표시 구현
                    } finally {
                        _uiState.update { it.copy(isSaving = false) }
                    }
                }
            }

            is TextMemoFormAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is TextMemoFormAction.OnContentChange -> _uiState.update { it.copy(content = action.content) }

            is TextMemoFormAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is TextMemoFormAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }

            TextMemoFormAction.CloseExitConfirmationDialog -> _uiState.update { it.copy(isExitConfirmationDialogVisible = false) }

            TextMemoFormAction.CloseScreen -> {
                _uiState.update { it.copy(isExitConfirmationDialogVisible = false) }
                _event.trySend(TextMemoFormEvent.NavigateBack)
            }
        }
    }

    private suspend fun loadTextMemo() {
        val result = bookStorageRepository.getTextMemo(
            userId = userId,
            bookId = bookId,
            memoId = memoId
        )

        _uiState.update { state ->
            val isSamePage = result.startPage == result.endPage

            state.copy(
                title = result.title,
                content = result.content,
                startPage = result.startPage.toString(),
                endPage = if (isSamePage) "" else result.endPage.toString(),
                originalTitle = result.title,
                originalContent = result.content,
                originalStartPage = result.startPage.toString(),
                originalEndPage = if (isSamePage) "" else result.endPage.toString(),
                isLoading = false
            )
        }
    }

    private suspend fun saveTextMemo() {
        if (memoId.isBlank()) {
            bookStorageRepository.addTextMemo(
                userId = userId,
                bookId = bookId,
                memo = _uiState.value.toUiModel()
            )
        } else {
            bookStorageRepository.updateTextMemo(
                userId = userId,
                bookId = bookId,
                memoId = memoId,
                memo = _uiState.value.toUiModel()
            )
        }
    }
}