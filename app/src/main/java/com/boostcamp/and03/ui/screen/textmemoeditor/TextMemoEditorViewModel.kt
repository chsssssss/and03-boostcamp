package com.boostcamp.and03.ui.screen.textmemoeditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.model.request.toRequest
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.textmemoeditor.model.TextMemoEditorAction
import com.boostcamp.and03.ui.screen.textmemoeditor.model.TextMemoEditorEvent
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
class TextMemoEditorViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val textMemoEditorRoute = savedStateHandle.toRoute<Route.TextMemoEditor>()
    private val bookId = textMemoEditorRoute.bookId

    private val _uiState = MutableStateFlow(TextMemoEditorUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<TextMemoEditorEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

    init {
        viewModelScope.launch {
            loadTotalPage()
        }
    }

    fun onAction(action: TextMemoEditorAction) {
        when (action) {
            TextMemoEditorAction.OnBackClick -> _event.trySend(TextMemoEditorEvent.NavigateBack)

            TextMemoEditorAction.OnSaveClick -> {
                if (uiState.value.isSaveable) {
                    viewModelScope.launch {
                        saveTextMemo()
                        _event.send(TextMemoEditorEvent.NavigateBack)
                    }
                }
            }

            is TextMemoEditorAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is TextMemoEditorAction.OnContentChange -> _uiState.update { it.copy(content = action.content) }

            is TextMemoEditorAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is TextMemoEditorAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    private suspend fun loadTotalPage() {
        val result = bookStorageRepository.getBookDetail(
            userId = userId,
            bookId = bookId
        )
        if (result != null) {
            _uiState.update { it.copy(totalPage = result.totalPage) }
        }
    }

    private suspend fun saveTextMemo() {
        bookStorageRepository.addTextMemo(
            userId = userId,
            bookId = bookId,
            memo = _uiState.value.toRequest()
        )
    }
}