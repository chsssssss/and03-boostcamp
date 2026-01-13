package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddTextMemoViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(AddTextMemoUiState())
    val uiState = _uiState.asStateFlow()

    fun saveTextMemo() {
        // TODO: 텍스트 메모 저장 기능 구현
    }
}