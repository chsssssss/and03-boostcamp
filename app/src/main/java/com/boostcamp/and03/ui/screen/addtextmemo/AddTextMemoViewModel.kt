package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.lifecycle.ViewModel
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.screen.addtextmemo.model.AddTextMemoAction
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
class AddTextMemoViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AddTextMemoUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<AddTextMemoEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: AddTextMemoAction) {
        when (action) {
            AddTextMemoAction.OnBackClick -> _event.trySend(AddTextMemoEvent.NavigateBack)

            AddTextMemoAction.OnSaveClick -> if (uiState.value.isSaveable) { saveTextMemo() }

            is AddTextMemoAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }

            is AddTextMemoAction.OnContentChange -> _uiState.update { it.copy(content = action.content) }

            is AddTextMemoAction.OnStartPageChange -> _uiState.update { it.copy(startPage = action.startPage) }

            is AddTextMemoAction.OnEndPageChange -> _uiState.update { it.copy(endPage = action.endPage) }
        }
    }

    fun setTotalPage(totalPage: Int) {
        _uiState.update { it.copy(totalPage = totalPage) }
    }

    private fun saveTextMemo() {
        // TODO: 텍스트 메모 저장 기능 구현
    }
}