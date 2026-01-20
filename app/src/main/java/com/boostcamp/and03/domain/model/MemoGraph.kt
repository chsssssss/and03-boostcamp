package com.boostcamp.and03.domain.model

data class MemoGraph(
    val nodes: Map<String, MemoNode> = emptyMap(),
    val edges: List<Edge> = emptyList()
)