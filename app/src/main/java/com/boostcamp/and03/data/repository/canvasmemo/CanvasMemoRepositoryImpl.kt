package com.boostcamp.and03.data.repository.canvasmemo

import com.boostcamp.and03.data.datasource.remote.canvasmemo.CanvasMemoDataSource
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.repository.CanvasMemoRepository
import javax.inject.Inject

class CanvasMemoRepositoryImpl @Inject constructor(
    private val canvasMemoDataSource: CanvasMemoDataSource
) : CanvasMemoRepository {
    override suspend fun loadCanvasMemo(graphId: String): MemoGraph {
        val response = canvasMemoDataSource.getCanvasMemo(graphId)
        return MemoGraphFactory.fromResponse(response)
    }
}