package com.boostcamp.and03.domain.factory

import androidx.compose.ui.geometry.Offset
import com.boostcamp.and03.data.mapper.toDomain
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse
import com.boostcamp.and03.domain.model.Edge
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.model.MemoNode
import kotlin.collections.associate

object MemoGraphFactory {

    // 빈 그래프 생성
    fun empty(): MemoGraph =
        MemoGraph(
            nodes = emptyMap(),
            edges = emptyList()
        )

    // CanvasMemoResponse → MemoGraph 변환
    fun fromResponse(response: CanvasMemoResponse): MemoGraph {
        return MemoGraph(
            nodes = response.graph.nodes.associate { it.id to it.toDomain() },
            edges = response.graph.edges.map { it.toDomain() }
        )
    }

    fun from(
        nodes: List<MemoNode>,
        edges: List<Edge>
    ): MemoGraph {
        return MemoGraph(
            nodes = nodes.associateBy { it.id },
            edges = edges
        )
    }

    fun fromNodesAndEdges(
        nodes: List<NodeResponse>,
        edges: List<EdgeResponse>
    ): MemoGraph {

        val nodeMap: Map<String, MemoNode> = nodes.associate { node ->
            val offset = Offset(node.x, node.y)

            val memoNode = when (node.nodeType) {
                "CHARACTER" -> MemoNode.CharacterNode(
                    id = node.id,
                    name = node.title,
                    description = node.content,
                    imageUrl = node.imageUrl,
                    offset = offset
                )

                "QUOTE" -> MemoNode.QuoteNode(
                    id = node.id,
                    content = node.content,
                    page = node.page,
                    offset = offset
                )

                else -> throw IllegalArgumentException(
                    "Unknown nodeType: ${node.nodeType}"
                )
            }

            node.id to memoNode
        }

        val edgeList = edges.map {
            Edge(
                id = it.id,
                fromId = it.fromNodeId,
                toId = it.toNodeId,
                name = it.relationText
            )
        }

        return MemoGraph(
            nodes = nodeMap,
            edges = edgeList
        )
    }

    fun createSample(): MemoGraph {
        val node1 = MemoNode.CharacterNode(
            id = "1",
            name = "어린 왕자",
            description = "B612 소행성에서 온 아이",
            offset = Offset(0f, 0f),
            imageUrl = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg"
        )
        val node2 = MemoNode.CharacterNode(
            id = "2",
            name = "여우",
            description = "길들여짐의 의미를 알려준 친구",
            offset = Offset(100f, 0f),
            imageUrl = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg"
        )
        val node3 = MemoNode.QuoteNode(
            id = "3",
            content = "가장 중요한 것은 눈에 보이지 않아.",
            page = 82,
            offset = Offset(150f, 0f)
        )

        val nodes = mapOf(
            node1.id to node1,
            node2.id to node2,
            node3.id to node3
        )

        return MemoGraph(nodes, emptyList())
    }
}
