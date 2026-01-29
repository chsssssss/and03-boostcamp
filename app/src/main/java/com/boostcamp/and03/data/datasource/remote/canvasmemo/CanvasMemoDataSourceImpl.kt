package com.boostcamp.and03.data.datasource.remote.canvasmemo

import android.util.Log
import com.boostcamp.and03.data.mapper.MemoEdgeMapper
import com.boostcamp.and03.data.mapper.MemoNodeMapper
import com.boostcamp.and03.data.mapper.MemoResponseMapper
import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CanvasMemoDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CanvasMemoDataSource {
    override suspend fun getCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): CanvasMemoResponse {
        try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)
                .get()
                .await()

            Log.d("CanvasMemoDataSourceImpl", "getCanvasMemo: $snapshot")
            Log.d("CanvasMemoDataSourceImpl", "getCanvasMemo: ${snapshot.data}")


            if (!snapshot.exists()) {
                throw IllegalStateException("Memo not found: $memoId")
            }

            val data = snapshot.data
                ?: throw IllegalStateException("Memo data is null: $memoId")

            val response = MemoResponseMapper.fromFirebase(snapshot.id, data)
                ?: throw IllegalStateException("Invalid memo type: ${data["type"]}")

            if (response !is CanvasMemoResponse) {
                throw IllegalStateException("Memo is not Canvas type: $memoId")
            }

            return response
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "getCanvasMemo error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getCanvasMemoNodes(
        userId: String,
        bookId: String,
        memoId: String
    ): List<NodeResponse> {
        val snapshot = db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)
            .collection("node")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            Log.d("CanvasMemoDataSourceImpl", "getCanvasMemoNodes: $it")
            Log.d("CanvasMemoDataSourceImpl", "getCanvasMemoNodes: ${it.id}")
            MemoNodeMapper.fromFirebase(it.id, it.data ?: return@mapNotNull null)
        }
    }

    override suspend fun getCanvasMemoEdges(
        userId: String,
        bookId: String,
        memoId: String
    ): List<EdgeResponse> {
        val snapshot = db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)
            .collection("edge")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            Log.d("CanvasMemoDataSourceImpl", "getCanvasMemoEdges: $it")
            MemoEdgeMapper.fromFirebase(it.id, it.data ?: return@mapNotNull null)
        }
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

            val batch = db.batch()

            val nodeCollection = collectionRef.collection("node")
            val edgeCollection = collectionRef.collection("edge")

            val nodeSnapshot = nodeCollection.get().await()
            nodeSnapshot.documents.forEach { doc ->
                batch.delete(doc.reference)
            }

            val edgeSnapshot = edgeCollection.get().await()
            edgeSnapshot.documents.forEach { doc ->
                batch.delete(doc.reference)
            }

            batch.set(
                collectionRef,
                mapOf("updatedTime" to FieldValue.serverTimestamp()),
                SetOptions.merge()
            )

            graph.nodes.forEach { node ->
                val newNodeRef = nodeCollection.document(node.id)
                batch.set(newNodeRef, node)
            }

            graph.edges.forEach { edge ->
                val newEdgeRef = edgeCollection.document(edge.id)
                batch.set(newEdgeRef, edge)
            }

            batch.commit().await()

            Log.d("CharacterDataSourceImpl", "CanvasMemo added: ${collectionRef.id}")
        } catch (e: Exception) {
            Log.e("CharacterDataSourceImpl", "Failed to add canvas memo: ${e.message}")
            throw e
        }
    }
}