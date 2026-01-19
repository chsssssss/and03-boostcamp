package com.boostcamp.and03.ui.screen.textmemoeditor

import androidx.lifecycle.ViewModel
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.screen.textmemoeditor.model.TextMemoEditorAction
import com.boostcamp.and03.ui.screen.textmemoeditor.model.TextMemoEditorEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TextMemoEditorViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(TextMemoEditorUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<TextMemoEditorEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: TextMemoEditorAction) {
        when (action) {
            TextMemoEditorAction.OnBackClick -> _event.trySend(TextMemoEditorEvent.NavigateBack)

            TextMemoEditorAction.OnSaveClick -> if (uiState.value.isSaveable) { saveTextMemo() }

            is TextMemoEditorAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is TextMemoEditorAction.OnContentChange -> _uiState.update { it.copy(content = action.content) }

            is TextMemoEditorAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is TextMemoEditorAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    fun setTotalPage(totalPage: Int) {
        _uiState.update { it.copy(totalPage = totalPage) }
    }

    private fun saveTextMemo() {
        // TODO: 텍스트 메모 저장 기능 구현
    }
}