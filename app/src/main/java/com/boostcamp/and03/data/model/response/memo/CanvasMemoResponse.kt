package com.boostcamp.and03.data.model.response.memo

data class CanvasMemoResponse(
    override val id: String,
    override val title: String,
    override val createdAt: String,
    override val type: String,
    val startPage: Int,
    val endPage: Int,
    val graph: GraphResponse
): MemoResponse

data class GraphResponse(
    val nodes: List<NodeResponse>,
    val edges: List<EdgeResponse>,
)

data class NodeResponse(
    val id: String,
    val title: String,
    val content: String,
    val nodeType: String,
    val x: Float,
    val y: Float,
)

data class EdgeResponse(
    val id: String = "",
    val fromNodeId: String = "",
    val toNodeId: String = "",
    val relationText: String = ""
)