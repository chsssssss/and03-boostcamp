package com.boostcamp.and03.data.datasource.remote.canvasmemo

import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse

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
    ): List<NodeResponse>

    suspend fun getCanvasMemoEdges(
        userId: String,
        bookId: String,
        memoId: String
    ): List<EdgeResponse>

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: GraphRequest
    )

}