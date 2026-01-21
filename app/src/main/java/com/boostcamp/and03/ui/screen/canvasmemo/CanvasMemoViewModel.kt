package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CanvasMemoViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CanvasMemoUiState())
    val uiState: StateFlow<CanvasMemoUiState> = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: CanvasMemoAction) {
        when (action) {
            CanvasMemoAction.ClickBack -> handleClickBack()
            CanvasMemoAction.CloseRelationDialog -> handleCloseRelationDialog()
            is CanvasMemoAction.OpenRelationDialog -> handleOpenRelationDialog(action)
            CanvasMemoAction.CloseAddCharacterDialog -> handleCloseAddCharacterDialog()
        }
    }

    private fun handleClickBack() {
        _event.trySend(CanvasMemoEvent.NavToBack)
    }

    private fun handleCloseRelationDialog() {
        _uiState.update {
            it.copy(
                isRelationDialogVisible = false,
                relationSelection = null,
                relationNameState = TextFieldState()
            )
        }
    }

    private fun handleOpenRelationDialog(action: CanvasMemoAction.OpenRelationDialog) {
        _uiState.update {
            it.copy(
                isRelationDialogVisible = true,
                relationSelection = RelationSelection(
                    fromNodeId = action.fromNodeId,
                    toNodeId = action.toNodeId
                ),
                relationNameState = TextFieldState()
            )
        }
    }

    private fun handleCloseAddCharacterDialog() {
        _uiState.value = _uiState.value.copy(
            isAddCharacterDialogVisible = false,
            characterNameState = TextFieldState(),
            characterDescState = TextFieldState()
        )
    }
}