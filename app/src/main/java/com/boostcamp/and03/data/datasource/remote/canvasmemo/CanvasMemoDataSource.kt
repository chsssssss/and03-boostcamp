package com.boostcamp.and03.data.datasource.remote.canvasmemo

import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse
import kotlinx.coroutines.flow.Flow

interface CanvasMemoDataSource {
    suspend fun getCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
    ): CanvasMemoResponse

    suspend fun getCanvasMemoNodes(
        userId: String,
        bookId: String,
        memoId: String
    ): Flow<List<NodeResponse>>

    suspend fun getCanvasMemoEdges(
        userId: String,
        bookId: String,
        memoId: String
    ): Flow<List<EdgeResponse>>

    suspend fun saveCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: GraphRequest
    )

    suspend fun removeNode(
        userId: String,
        bookId: String,
        memoId: String,
        nodeIds: List<String>
    )
}