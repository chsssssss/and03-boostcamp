package com.boostcamp.and03.ui.screen.canvasmemoform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.canvasmemoform.model.toUiModel
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
class CanvasMemoFormViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val canvasMemoFormRoute = savedStateHandle.toRoute<Route.CanvasMemoForm>()
    private val bookId = canvasMemoFormRoute.bookId
    private val memoId = canvasMemoFormRoute.memoId
    private val totalPage = canvasMemoFormRoute.totalPage

    private val _uiState = MutableStateFlow(CanvasMemoFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoFormEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

    init {
        _uiState.update { it.copy(totalPage = totalPage) }
        viewModelScope.launch { loadTextMemo() }
    }

    fun onAction(action: CanvasMemoFormAction) {
        when (action) {
            CanvasMemoFormAction.OnBackClick -> _event.trySend(CanvasMemoFormEvent.NavigateBack)

            CanvasMemoFormAction.OnSaveClick -> {
                viewModelScope.launch {
                    if (_uiState.value.isSaving) return@launch

                    _uiState.update { it.copy(isSaving = true) }

                    try {
                        saveCanvasMemo()
                        _event.send(CanvasMemoFormEvent.NavigateBack)
                    } catch (e: Exception) {
                        // TODO: 오류 메시지 UI 표시 구현
                    } finally {
                        _uiState.update { it.copy(isSaving = false) }
                    }
                }
            }

            is CanvasMemoFormAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is CanvasMemoFormAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is CanvasMemoFormAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    private suspend fun loadTextMemo() {
        if (memoId.isBlank()) return

        val result = bookStorageRepository.getCanvasMemo(
            userId = userId,
            bookId = bookId,
            memoId = memoId
        )

        _uiState.update {
            val isSamePage = result.startPage == result.endPage

            it.copy(
                title = result.title,
                startPage = result.startPage.toString(),
                endPage = if (isSamePage) "" else result.endPage.toString()
            )
        }
    }

    private suspend fun saveCanvasMemo() {
        if (memoId.isBlank()) {
            bookStorageRepository.addCanvasMemo(
                userId = userId,
                bookId = bookId,
                memo = _uiState.value.toUiModel()
            )
        } else {
            bookStorageRepository.updateCanvasMemo(
                userId = userId,
                bookId = bookId,
                memoId = memoId,
                memo = _uiState.value.toUiModel()
            )
        }
    }
}