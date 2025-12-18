package com.boostcamp.and03.ui.screen.prototype.model

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class MemoNode(
    val id: String,
    val title: String,    // 메모 제목
    val content: String,    // 메모 내용
    val offset: Offset,    // 캔버스 내부 위치
    val size: IntSize = IntSize.Zero
)

// 화살표 연결 지점 계산
fun MemoNode.rightCenter(): Offset =
    offset + Offset(size.width.toFloat(), size.height / 2f)

fun MemoNode.leftCenter(): Offset =
    offset + Offset(0f, size.height / 2f)

data class Edge(
    val toId: String,
    val fromId: String,
    val name: String,
)

data class MemoGraph(
    val nodes: Map<String, MemoNode> = emptyMap(),
    val edges: List<Edge> = emptyList()
) {
//    private val _nodes = mutableMapOf<String, MemoNode>()
//    val nodes: Map<String, MemoNode> get() = _nodes
//
//    private val _edges = mutableListOf<Edge>()
//    val edges: List<Edge> get() = _edges

    fun addMemo(memo: MemoNode): MemoGraph {
//        _nodes[memo.id] = memo
        return copy(nodes = nodes + (memo.id to memo))
    }

    fun removeMemo(memoId: String): MemoGraph {
        return copy(
            nodes = nodes - memoId,
            edges = edges.filter { it.toId != memoId && it.fromId != memoId }
        )
    }

    fun connectMemo(fromId: String, toId: String, name: String): MemoGraph {
        return copy(edges = edges + Edge(toId, fromId, name))
    }

    fun disconnectMemo(fromId: String, toId: String): MemoGraph {
        return copy(
            edges = edges.filterNot { it.fromId == fromId && it.toId == toId }
        )
    }

    fun updateNodePosition(nodeId: String, newOffset: Offset): MemoGraph {
//        val node = nodes[nodeId] ?: return this
//        return copy(
//            nodes = nodes + (nodeId to node.copy(offset = newOffset))
//        )

//        return copy(
//            nodes = nodes.mapValues { (id, node) ->
//                if (id == nodeId) {
//                    Log.d("CanvasScreen", "updateNodePosition: $newOffset")
//                    Log.d("CanvasScreen", "updateNodePosition: ${node.offset}")
//                    node.copy(offset = newOffset)
//                }
//                else
//                    node
//            }
//        )

        return copy(
            nodes = nodes.mapValues { (id, node) ->
                if (id == nodeId) {
                    Log.d("CanvasScreen", "updateNodePosition: $newOffset")
                    Log.d("CanvasScreen", "updateNodePosition: ${node.offset}")
                    node.copy(offset = node.offset + newOffset)
                } else node
            }
        )


    }
}