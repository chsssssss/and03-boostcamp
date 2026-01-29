package com.boostcamp.and03.domain.repository

import com.boostcamp.and03.domain.model.MemoGraph

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
    ): MemoGraph

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: MemoGraph
    )
}
