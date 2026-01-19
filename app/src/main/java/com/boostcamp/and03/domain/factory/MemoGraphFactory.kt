package com.boostcamp.and03.domain.factory

import com.boostcamp.and03.domain.model.MemoGraph

//object MemoGraphFactory {
//
//    fun empty(): MemoGraph =
//        MemoGraph(
//            nodes = emptyMap(),
//            edges = emptyList()
//        )
//
//    fun fromDto(dto: MemoGraphDto): MemoGraph =
//        MemoGraph(
//            nodes = dto.nodes
//                .associateBy { it.id }
//                .mapValues { it.value.toDomain() },
//            edges = dto.edges.map { it.toDomain() }
//        )
//}
