package com.boostcamp.and03.ui.screen.addcanvasmemo

import androidx.lifecycle.ViewModel
import com.boostcamp.and03.ui.screen.addcanvasmemo.model.AddCanvasMemoAction
import com.boostcamp.and03.ui.screen.addcanvasmemo.model.AddCanvasMemoEvent
import com.boostcamp.and03.ui.screen.addtextmemo.model.AddTextMemoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddCanvasMemoViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(AddCanvasMemoUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<AddCanvasMemoEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: AddCanvasMemoAction) {
        when (action) {
            AddCanvasMemoAction.OnBackClick -> _event.trySend(AddCanvasMemoEvent.NavigateBack)

            AddCanvasMemoAction.OnSaveClick -> if (uiState.value.isSaveable) { saveCanvasMemo() }

            is AddCanvasMemoAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is AddCanvasMemoAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is AddCanvasMemoAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    fun saveCanvasMemo() {
        // TODO: 캔버스 메모 저장 기능 구현
    }
}