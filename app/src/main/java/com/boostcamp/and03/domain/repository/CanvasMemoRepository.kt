package com.boostcamp.and03.domain.repository

import com.boostcamp.and03.domain.model.MemoGraph
import kotlinx.coroutines.flow.Flow

interface CanvasMemoRepository {

    suspend fun loadCanvasMemo(
        userId: String,
        bookId: String,
        graphId: String
    ): MemoGraph

    suspend fun loadCanvasMemoDetail(
        userId: String,
        bookId: String,
        memoId: String
    ): Flow<MemoGraph>

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: MemoGraph
    )

    suspend fun removeNode(
        userId: String,
        bookId: String,
        memoId: String,
        nodeIds: List<String>
    )
}
