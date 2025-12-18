package com.boostcamp.and03.ui.screen.canvas.model

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

class MemoGraph {
    private val _nodes = mutableMapOf<String, MemoNode>()
    val nodes: Map<String, MemoNode> get() = _nodes

    private val _edges = mutableListOf<Edge>()
    val edges: List<Edge> get() = _edges

    fun addMemo(memo: MemoNode) {
        _nodes[memo.id] = memo
    }

    fun removeMemo(memoId: String) {
        _nodes.remove(memoId)
        _edges.removeAll { it.toId == memoId || it.fromId == memoId }
    }

    fun connectMemo(fromId: String, toId: String, name: String) {
        _edges.add(Edge(toId, fromId, name))
    }

    fun disconnectMemo(fromId: String, toId: String) {
        _edges.removeAll { it.toId == toId && it.fromId == fromId }
        _edges.removeAll { it.toId == toId && it.fromId == fromId }
    }
}