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
}
