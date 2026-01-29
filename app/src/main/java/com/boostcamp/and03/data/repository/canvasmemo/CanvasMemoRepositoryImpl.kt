package com.boostcamp.and03.data.repository.canvasmemo

import com.boostcamp.and03.data.datasource.remote.canvasmemo.CanvasMemoDataSource
import com.boostcamp.and03.data.mapper.toDomain
import com.boostcamp.and03.data.mapper.toRequest
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.repository.CanvasMemoRepository
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
    ): MemoGraph {
        val nodes = canvasMemoDataSource.getCanvasMemoNodes(
            userId = userId,
            bookId = bookId,
            memoId = memoId
        )
        .map { it.toDomain() }

        val edges = canvasMemoDataSource.getCanvasMemoEdges(
            userId = userId,
            bookId = bookId,
            memoId = memoId
        )
        .map { it.toDomain() }

        return MemoGraphFactory.from(nodes, edges)
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
}