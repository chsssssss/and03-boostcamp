package com.boostcamp.and03.domain.repository

import com.boostcamp.and03.domain.model.MemoGraph

interface CanvasMemoRepository {

    suspend fun loadCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): MemoGraph

    suspend fun saveCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: MemoGraph
    )
}
