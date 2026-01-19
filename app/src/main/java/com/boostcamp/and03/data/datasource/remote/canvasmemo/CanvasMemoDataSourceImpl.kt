package com.boostcamp.and03.data.datasource.remote.canvasmemo

import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CanvasMemoDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CanvasMemoDataSource {
    override suspend fun getCanvasMemo(graphId: String): CanvasMemoResponse {
        TODO("Not yet implemented")
    }
}