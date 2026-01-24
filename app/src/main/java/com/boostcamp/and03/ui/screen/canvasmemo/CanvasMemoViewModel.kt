package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.boostcamp.and03.domain.editor.CanvasMemoEditor
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.ui.screen.canvasmemo.model.toUiModel
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

    init {
        createInitialState()

        handleConnectNodes(
            CanvasMemoAction.ConnectNodes(
                fromId = "1",
                toId = "2",
                name = "로직 테스트 연결"
            )
        )
    }

    private fun createInitialState() {
        val sampleGraph = MemoGraphFactory.createSample()

        _uiState.update {
            it.copy(
                nodes = sampleGraph.nodes.mapValues { it.value.toUiModel() },
                edges = sampleGraph.edges.map { it.toUiModel() }
            )
        }
    }

    private fun getCurrentGraph(): MemoGraph {
        val nodes = _uiState.value.nodes.mapValues { it.value.node }
        val edges = _uiState.value.edges.map { it.edge }
        return MemoGraph(nodes, edges)
    }

    fun onAction(action: CanvasMemoAction) {
        when (action) {
            CanvasMemoAction.ClickBack -> handleClickBack()
            CanvasMemoAction.CloseRelationDialog -> handleCloseRelationDialog()
            is CanvasMemoAction.OpenRelationDialog -> handleOpenRelationDialog(action)
            CanvasMemoAction.CloseAddCharacterDialog -> handleCloseAddCharacterDialog()
            is CanvasMemoAction.MoveNode -> handleMoveNode(action)
            is CanvasMemoAction.ConnectNodes -> handleConnectNodes(action)
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

    private fun handleMoveNode(action: CanvasMemoAction.MoveNode) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.moveNode(action.nodeId, action.newOffset)

        _uiState.update { currentState ->
            currentState.copy(
                nodes = updatedGraph.nodes.mapValues { it.value.toUiModel() }
            )
        }
    }


    private fun handleConnectNodes(action: CanvasMemoAction.ConnectNodes) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.connectNode(action.fromId, action.toId, action.name)

        _uiState.update { currentState ->
            currentState.copy(
                edges = updatedGraph.edges.map { it.toUiModel() },
                isRelationDialogVisible = false
            )
        }
    }
}