package com.boostcamp.and03.domain.repository

import com.boostcamp.and03.domain.model.MemoGraph

interface CanvasMemoRepository {

    suspend fun loadCanvasMemo(graphId: String): MemoGraph

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: MemoGraph
    )
}
