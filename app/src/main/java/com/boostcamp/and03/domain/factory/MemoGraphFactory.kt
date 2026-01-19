package com.boostcamp.and03.domain.factory

import com.boostcamp.and03.data.mapper.toDomain
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.domain.model.MemoGraph

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
}
