package com.boostcamp.and03.domain.model

import androidx.compose.ui.geometry.Offset
import java.util.UUID

data class MemoGraph(
    val nodes: Map<String, MemoNode> = emptyMap(),
    val edges: List<Edge> = emptyList()
) {
    fun moveNode(nodeId: String, delta: Offset): MemoGraph {
        val node = nodes[nodeId] ?: return this
        val movedNode = when (node) {
            is MemoNode.CharacterNode -> node.copy(offset = node.offset + delta)
            is MemoNode.QuoteNode -> node.copy(offset = node.offset + delta)
        }
        return copy(nodes = nodes + (nodeId to movedNode))
    }

    fun removeNode(nodeId: String): MemoGraph {
        return copy(
            nodes = nodes - nodeId,
            edges = edges.filterNot { it.fromId == nodeId || it.toId == nodeId }
        )
    }

    fun connectNode(fromId: String, toId: String, name: String): MemoGraph {
        if (fromId == toId || edges.any { it.fromId == fromId && it.toId == toId }) return this

        val edgeId = UUID.randomUUID().toString()
        return copy(
            edges = edges + Edge(
                id = edgeId,
                toId = toId,
                fromId = fromId,
                name = name
            )
        )
    }

    fun disconnectNodes(fromId: String, toId: String): MemoGraph {
        return copy(edges = edges.filterNot { it.fromId == fromId && it.toId == toId })
    }

}