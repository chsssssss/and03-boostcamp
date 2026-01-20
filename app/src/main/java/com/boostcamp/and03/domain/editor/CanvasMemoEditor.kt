package com.boostcamp.and03.domain.editor

import androidx.compose.ui.geometry.Offset
import com.boostcamp.and03.domain.model.Edge
import com.boostcamp.and03.domain.model.MemoGraph

class CanvasMemoEditor(
    private val graph: MemoGraph
) {

    fun moveNode(nodeId: String, delta: Offset): MemoGraph {
        val node = graph.nodes[nodeId] ?: return graph
        return graph.copy(
            nodes = graph.nodes + (nodeId to node.copy(offset = node.offset + delta))
        )
    }

    fun removeNode(nodeId: String): MemoGraph {
        return graph.copy(
            nodes = graph.nodes - nodeId,
            edges = graph.edges.filterNot {
                it.fromId == nodeId || it.toId == nodeId
            }
        )
    }

    fun connectNode(fromId: String, toId: String, name: String = ""): MemoGraph {
        if (fromId == toId) return graph
        if (graph.edges.any { it.fromId == fromId && it.toId == toId }) {
            return graph
        }

        return graph.copy(
            edges = graph.edges + Edge(fromId, toId, name)
        )
    }

    fun disconnectNodes(fromId: String, toId: String): MemoGraph {
        return graph.copy(
            edges = graph.edges.filterNot { it.fromId == fromId && it.toId == toId }
        )
    }
}
