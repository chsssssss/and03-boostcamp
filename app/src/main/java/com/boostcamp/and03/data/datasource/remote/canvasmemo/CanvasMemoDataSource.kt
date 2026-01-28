package com.boostcamp.and03.data.datasource.remote.canvasmemo

import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse

interface CanvasMemoDataSource {
    suspend fun getCanvasMemo(graphId: String): CanvasMemoResponse

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: GraphRequest
    )

}