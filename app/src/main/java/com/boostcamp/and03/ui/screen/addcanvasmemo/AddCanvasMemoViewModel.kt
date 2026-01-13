package com.boostcamp.and03.ui.screen.addcanvasmemo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddCanvasMemoViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(AddCanvasMemoUiState())
    val uiState = _uiState.asStateFlow()

    fun saveCanvasMemo() {
        // TODO: 캔버스 메모 저장 기능 구현
    }
}