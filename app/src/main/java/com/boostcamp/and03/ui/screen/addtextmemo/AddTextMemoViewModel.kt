package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddTextMemoViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(AddTextMemoUiState())
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun updateStartPage(startPage: String) {
        _uiState.update { it.copy(startPage = startPage) }
    }

    fun updateEndPage(endPage: String) {
        _uiState.update { it.copy(endPage = endPage) }
    }

    fun saveTextMemo() {
        // TODO: 텍스트 메모 저장 기능 구현
    }
}