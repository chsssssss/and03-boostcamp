package com.boostcamp.and03.ui.screen.canvasmemoeditor

import androidx.lifecycle.ViewModel
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.screen.canvasmemoeditor.model.CanvasMemoEditorAction
import com.boostcamp.and03.ui.screen.canvasmemoeditor.model.CanvasMemoEditorEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CanvasMemoEditorViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CanvasMemoEditorUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoEditorEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: CanvasMemoEditorAction) {
        when (action) {
            CanvasMemoEditorAction.OnBackClick -> _event.trySend(CanvasMemoEditorEvent.NavigateBack)

            CanvasMemoEditorAction.OnSaveClick -> if (uiState.value.isSaveable) { saveCanvasMemo() }

            is CanvasMemoEditorAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is CanvasMemoEditorAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is CanvasMemoEditorAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    fun saveCanvasMemo() {
        // TODO: 캔버스 메모 저장 기능 구현
    }
}