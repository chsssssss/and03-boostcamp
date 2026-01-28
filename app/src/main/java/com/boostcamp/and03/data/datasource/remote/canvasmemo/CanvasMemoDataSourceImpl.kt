package com.boostcamp.and03.data.datasource.remote.canvasmemo

import android.util.Log
import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CanvasMemoDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CanvasMemoDataSource {
    override suspend fun getCanvasMemo(graphId: String): CanvasMemoResponse {
        TODO("Not yet implemented")
    }

    override suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: GraphRequest
    ) {
        try {
            val collectionRef = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)

            val nodeSnapshot = collectionRef.collection("node").get().await()
            nodeSnapshot.documents.forEach { doc ->
                doc.reference.delete()
            }

            val edgeSnapshot = collectionRef.collection("edge").get().await()
            edgeSnapshot.documents.forEach { doc ->
                doc.reference.delete()
            }

            collectionRef.set(
                mapOf("updatedTime" to FieldValue.serverTimestamp()),
                SetOptions.merge()
            )

            graph.nodes.forEach { node ->
                collectionRef
                    .collection("node")
                    .document()
                    .set(node)
            }

            graph.edges.forEach { edge ->
                collectionRef
                    .collection("edge")
                    .document()
                    .set(edge)
            }

            Log.d("CharacterDataSourceImpl", "CanvasMemo added: ${collectionRef.id}")
        } catch (e: Exception) {
            Log.e("CharacterDataSourceImpl", "Failed to add canvas memo: ${e.message}")
            throw e
        }
    }
}