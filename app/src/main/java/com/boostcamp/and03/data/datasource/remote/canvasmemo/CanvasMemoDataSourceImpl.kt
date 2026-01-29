package com.boostcamp.and03.data.datasource.remote.canvasmemo

import android.util.Log
import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.GraphResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse
import com.google.firebase.firestore.DocumentReference
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
        return try {
            val memoRef = getMemoRef(
                userId,
                bookId,
                memoId
            )

            val canvasMemoSnapshot = memoRef.get().await()

            if (!canvasMemoSnapshot.exists()) {
                throw NoSuchElementException("Canvas memo not found: $memoId")
            }

            val data = canvasMemoSnapshot.data
                ?: throw IllegalStateException("Canvas memo data is null: $memoId")

            val nodes = getNodes(memoRef)
            val edges = getEdges(memoRef)

            CanvasMemoResponse(
                id = canvasMemoSnapshot.id,
                title = data["title"] as? String ?: "",
                createdAt = data["createdAt"] as? String ?: "",
                type = "CANVAS",
                startPage = (data["startPage"] as? Long)?.toInt() ?: 0,
                endPage = (data["endPage"] as? Long)?.toInt() ?: 0,
                graph = GraphResponse(
                    nodes = nodes,
                    edges = edges
                )
            )

        } catch (e: Exception) {
            Log.e("CanvasMemoDataSourceImpl", "Failed to get canvas memo: ${e.message}")
            throw e
        }
    }

    override suspend fun saveCanvasMemo(
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
                val newNodeRef = nodeCollection.document()
                batch.set(newNodeRef, node)
            }

            graph.edges.forEach { edge ->
                val newEdgeRef = edgeCollection.document()
                batch.set(newEdgeRef, edge)
            }

            batch.commit().await()

            Log.d("CanvasMemoDataSourceImpl", "CanvasMemo added: ${collectionRef.id}")
        } catch (e: Exception) {
            Log.e("CanvasMemoDataSourceImpl", "Failed to add canvas memo: ${e.message}")
            throw e
        }
    }

    private suspend fun getNodes(memoRef: DocumentReference): List<NodeResponse> {
        val nodeSnapshot = memoRef
            .collection("node")
            .get()
            .await()

        return nodeSnapshot.documents.map { document ->
            val nodeData = document.data ?: emptyMap()
            NodeResponse(
                id = document.id,
                title = nodeData["title"] as? String ?: "",
                content = nodeData["content"] as? String ?: "",
                imageUrl = nodeData["imageUrl"] as? String ?: "",
                nodeType = nodeData["nodeType"] as? String ?: "",
                page = (nodeData["page"] as? Long)?.toInt() ?: 0,
                x = (nodeData["x"] as? Double)?.toFloat() ?: 0f,
                y = (nodeData["y"] as? Double)?.toFloat() ?: 0f
            )
        }
    }

    private suspend fun getEdges(memoRef: DocumentReference): List<EdgeResponse> {
        val edgeSnapshot = memoRef
            .collection("edge")
            .get()
            .await()

        return edgeSnapshot.documents.map { document ->
            val edgeData = document.data ?: emptyMap()
            EdgeResponse(
                id = document.id,
                fromNodeId = edgeData["fromId"] as? String ?: "",
                toNodeId = edgeData["toId"] as? String ?: "",
                relationText = edgeData["relationText"] as? String ?: ""
            )
        }
    }

    private fun getMemoRef(
        userId: String,
        bookId: String,
        memoId: String
    ) : DocumentReference {
        return db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)
    }
}