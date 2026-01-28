package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.lifecycle.ViewModel
import com.boostcamp.and03.domain.editor.CanvasMemoEditor
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
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
    }

    private fun createInitialState() {
        val sampleGraph = MemoGraphFactory.createSample()

        _uiState.update {
            it.copy(
                nodes = sampleGraph.nodes.mapValues { it.value.toUiModel() },
                edges = sampleGraph.edges.map { it.toUiModel() },
                characters = emptyList() // 추후 서버 연동
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
            CanvasMemoAction.CloseAddCharacterDialog -> handleCloseAddCharacterDialog()
            CanvasMemoAction.CloseAddNodeSheet -> handleCloseAddNodeSheet()
            is CanvasMemoAction.OpenRelationDialog -> handleOpenRelationDialog(action)
            is CanvasMemoAction.MoveNode -> handleMoveNode(action)
            is CanvasMemoAction.ConnectNodes -> handleConnectNodes(action)
            is CanvasMemoAction.OnBottomBarClick -> handleBottomBarClick(action)
        }
    }

    private fun handleClickBack() {
        _event.trySend(CanvasMemoEvent.NavToBack)
    }

    private fun handleCloseRelationDialog() {
        _uiState.update {
            it.copy(
                isRelationDialogVisible = false,
                relationSelection = null
            )
        }
    }

    private fun handleCloseAddCharacterDialog() {
        _uiState.update {
            it.copy(isAddCharacterDialogVisible = false)
        }
    }

    private fun handleCloseAddNodeSheet() {
        _uiState.update {
            it.copy(isAddNodeSheetVisible = false)
        }
    }

    private fun handleOpenRelationDialog(action: CanvasMemoAction.OpenRelationDialog) {
        _uiState.update {
            it.copy(
                isRelationDialogVisible = true,
                relationSelection = RelationSelection(
                    fromNodeId = action.fromNodeId,
                    toNodeId = action.toNodeId
                )
            )
        }
    }

    private fun handleMoveNode(action: CanvasMemoAction.MoveNode) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.moveNode(action.nodeId, action.newOffset)
        val movedNode = updatedGraph.nodes[action.nodeId] ?: return

        _uiState.update {
            it.copy(
                nodes = it.nodes + (action.nodeId to movedNode.toUiModel())
            )
        }
    }

    private fun handleConnectNodes(action: CanvasMemoAction.ConnectNodes) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.connectNode(action.fromId, action.toId, action.name)

        _uiState.update {
            it.copy(
                edges = updatedGraph.edges.map { it.toUiModel() },
                isRelationDialogVisible = false
            )
        }
    }

    private fun handleBottomBarClick(action: CanvasMemoAction.OnBottomBarClick) {
        _uiState.update {
            it.copy(
                selectedBottomBarType = action.type,
                isAddNodeSheetVisible = action.type == MainBottomBarType.NODE
            )
        }
    }
}
