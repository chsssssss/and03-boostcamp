package com.boostcamp.and03.ui.screen.textmemoform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
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
    private val textMemoEditorRoute = savedStateHandle.toRoute<Route.TextMemoForm>()
    private val bookId = textMemoEditorRoute.bookId
    private val memoId = textMemoEditorRoute.memoId

    private val _uiState = MutableStateFlow(TextMemoFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<TextMemoFormEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

    init {
        viewModelScope.launch {
            loadTotalPage()
        }
    }

    fun onAction(action: TextMemoFormAction) {
        when (action) {
            TextMemoFormAction.OnBackClick -> _event.trySend(TextMemoFormEvent.NavigateBack)

            TextMemoFormAction.OnSaveClick -> {
                if (uiState.value.isSaveable) {
                    viewModelScope.launch {
                        saveTextMemo()
                        _event.send(TextMemoFormEvent.NavigateBack)
                    }
                }
            }

            is TextMemoFormAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is TextMemoFormAction.OnContentChange -> _uiState.update { it.copy(content = action.content) }

            is TextMemoFormAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is TextMemoFormAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
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
        if (memoId == "") {
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