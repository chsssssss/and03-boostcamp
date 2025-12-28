package com.boostcamp.and03.ui.screen.prototype.screen

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.boostcamp.and03.ui.screen.prototype.model.MemoGraph
import com.boostcamp.and03.ui.screen.prototype.model.MemoNode

class CanvasViewModel : ViewModel() {

    private val _graph = MutableStateFlow(
        MemoGraph()
            .addMemo(MemoNode("1", "인물 A", "설명 A", Offset(100f, 200f)))
            .addMemo(MemoNode("2", "인물 B", "설명 B", Offset(700f, 200f)))
            .addMemo(MemoNode("3", "인물 C", "설명 C", Offset(100f, 600f)))
            .addMemo(MemoNode("4", "인물 D", "설명 D", Offset(700f, 600f)))
            .addMemo(MemoNode("5", "인물 E", "설명 E", Offset(100f, 1000f)))
            .addMemo(MemoNode("6", "인물 F", "설명 F", Offset(700f, 1000f)))
    )
    val graph: StateFlow<MemoGraph> = _graph.asStateFlow()

    fun moveNode(nodeId: String, delta: Offset) {
        _graph.update { current ->
            current.updateNodePosition(nodeId, delta)
        }
    }


    fun connect(fromId: String, toId: String, name: String) {
        _graph.update { current ->
            current.connectMemo(fromId, toId, name)
        }
    }

    fun snapToGrid(gridSize: Float) {
        _graph.update { current ->
            val snapped = snapNodesToGrid(current.nodes.values.toList(), gridSize)
            current.copy(nodes = snapped.associateBy { it.id })
        }
    }
}
