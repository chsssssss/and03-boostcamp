package com.boostcamp.and03.data.repository.canvasmemo

import com.boostcamp.and03.data.datasource.remote.canvasmemo.CanvasMemoDataSource
import com.boostcamp.and03.data.mapper.toDomain
import com.boostcamp.and03.data.mapper.toRequest
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.repository.CanvasMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CanvasMemoRepositoryImpl @Inject constructor(
    private val canvasMemoDataSource: CanvasMemoDataSource
) : CanvasMemoRepository {
    override suspend fun loadCanvasMemo(
        userId: String,
        bookId: String,
        graphId: String
    ): MemoGraph {
        val response = canvasMemoDataSource.getCanvasMemo(
            userId = userId,
            bookId = bookId,
            memoId = graphId
        )
        return MemoGraphFactory.fromResponse(response)
    }

    override suspend fun loadCanvasMemoDetail(
        userId: String,
        bookId: String,
        memoId: String
    ): Flow<MemoGraph> {
        val nodeFlow = canvasMemoDataSource
            .getCanvasMemoNodes(userId, bookId, memoId)
            .map { nodes -> nodes.map { it.toDomain() } }

        val edgeFlow = canvasMemoDataSource
            .getCanvasMemoEdges(userId, bookId, memoId)
            .map { edges -> edges.map { it.toDomain() } }

        return combine(nodeFlow, edgeFlow) { nodes, edges ->
            MemoGraphFactory.from(nodes, edges)
        }
    }

    override suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: MemoGraph
    ) {
        val request = graph.toRequest()
        return canvasMemoDataSource.addCanvasMemo(
            userId = userId,
            bookId = bookId,
            memoId = memoId,
            graph = request
        )
    }

    override suspend fun removeNode(
        userId: String,
        bookId: String,
        memoId: String,
        nodeIds: List<String>
    ) {
        return canvasMemoDataSource.removeNode(
            userId = userId,
            bookId = bookId,
            memoId = memoId,
            nodeIds = nodeIds
        )
    }
}