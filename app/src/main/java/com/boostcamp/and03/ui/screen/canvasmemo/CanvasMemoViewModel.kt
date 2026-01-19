package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CanvasMemoViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CanvasMemoUiState())
    val uiState: StateFlow<CanvasMemoUiState> = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: CanvasMemoAction) {
        when (action) {
            is CanvasMemoAction.ClickBack -> {
                _event.trySend(CanvasMemoEvent.NavToBack)
            }
        }
    }
}