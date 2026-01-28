package com.boostcamp.and03.data.mapper

import androidx.compose.ui.geometry.Offset
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.GraphResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse
import com.boostcamp.and03.domain.model.Edge
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.model.MemoNode


// NodeResponse → MemoNode
fun NodeResponse.toDomain(): MemoNode =
    when (nodeType) {
        "CHARACTER" -> MemoNode.CharacterNode(
            id = id,
            name = title,
            description = content,
            offset = Offset(x, y)
        )

        "QUOTE" -> MemoNode.QuoteNode(
            id = id,
            content = content,
            page = requireNotNull(page),
            offset = Offset(x, y)
        )

        else -> error("Unknown node type: $nodeType")
    }


// EdgeResponse → Edge
fun EdgeResponse.toDomain(): Edge =
    Edge(
        fromId = fromNodeId,
        toId = toNodeId,
        name = relationText
    )

// GraphResponse → MemoGraph
fun GraphResponse.toDomain(): MemoGraph {
    val nodesMap = nodes.associate { it.id to it.toDomain() }
    val edgesList = edges.map { it.toDomain() }
    return MemoGraph(nodes = nodesMap, edges = edgesList)
}

// CanvasMemoResponse → MemoGraph (graph만 변환)
fun CanvasMemoResponse.toDomain(): MemoGraph =
    graph.toDomain()