package com.boostcamp.and03.domain.factory

import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.domain.model.Edge
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.model.MemoNode
import androidx.compose.ui.geometry.Offset

object MemoGraphFactory {

    // 빈 그래프 생성
    fun empty(): MemoGraph =
        MemoGraph(
            nodes = emptyMap(),
            edges = emptyList()
        )

    // CanvasMemoResponse → MemoGraph 변환
    fun fromResponse(response: CanvasMemoResponse): MemoGraph {
        val nodesMap = response.graph.nodes.associate { node ->
            node.id to MemoNode(
                id = node.id,
                title = node.title,
                content = node.content,
                offset = Offset(node.x, node.y)
            )
        }

        val edgesList = response.graph.edges.map { edge ->
            Edge(
                fromId = edge.fromNodeId,
                toId = edge.toNodeId,
                name = edge.relationText
            )
        }

        return MemoGraph(
            nodes = nodesMap,
            edges = edgesList
        )
    }
}
