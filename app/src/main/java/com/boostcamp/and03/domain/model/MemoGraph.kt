package com.boostcamp.and03.domain.model

import com.boostcamp.and03.ui.screen.prototype.model.MemoNode

data class MemoGraph(
    val nodes: Map<String, MemoNode> = emptyMap(),
    val edges: List<Edge> = emptyList()
)