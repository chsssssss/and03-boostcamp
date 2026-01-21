package com.boostcamp.and03.ui.screen.canvasmemoform

import androidx.lifecycle.ViewModel
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CanvasMemoFormViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CanvasMemoFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoFormEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: CanvasMemoFormAction) {
        when (action) {
            CanvasMemoFormAction.OnBackClick -> _event.trySend(CanvasMemoFormEvent.NavigateBack)

            CanvasMemoFormAction.OnSaveClick -> if (uiState.value.isSaveable) { saveCanvasMemo() }

            is CanvasMemoFormAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is CanvasMemoFormAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is CanvasMemoFormAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    fun saveCanvasMemo() {
        // TODO: 캔버스 메모 저장 기능 구현
    }
}