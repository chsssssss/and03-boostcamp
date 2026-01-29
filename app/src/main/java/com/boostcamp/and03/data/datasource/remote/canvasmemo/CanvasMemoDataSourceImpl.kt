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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

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
    ): Flow<List<NodeResponse>> = callbackFlow {
        val snapshot = db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)
            .collection("node")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot == null) return@addSnapshotListener

                val nodes = snapshot.documents.mapNotNull { doc ->
                    Log.d("CanvasMemoDataSourceImpl", "node snapshot: ${doc.id}")
                    MemoNodeMapper.fromFirebase(
                        doc.id,
                        doc.data ?: return@mapNotNull null
                    )
                }

                trySend(nodes)
            }

        awaitClose { snapshot.remove() }
    }

    override suspend fun getCanvasMemoEdges(
        userId: String,
        bookId: String,
        memoId: String
    ): Flow<List<EdgeResponse>> = callbackFlow {
        val snapshot = db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)
            .collection("edge")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot == null) return@addSnapshotListener

                val edges = snapshot.documents.mapNotNull { doc ->
                    Log.d("CanvasMemoDataSourceImpl", "edge snapshot: ${doc.id}")
                    MemoEdgeMapper.fromFirebase(
                        doc.id,
                        doc.data ?: return@mapNotNull null
                    )
                }

                trySend(edges)
            }

        awaitClose { snapshot.remove() }
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

    override suspend fun removeNode(
        userId: String,
        bookId: String,
        memoId: String,
        nodeIds: List<String>
    ) {
        val memoRef = db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)

        val nodeCollection = memoRef.collection("node")
        val edgeCollection = memoRef.collection("edge")

        val batch = db.batch()

        nodeIds.forEach { nodeId ->
            batch.delete(nodeCollection.document(nodeId))
        }

        val edgeFrom = edgeCollection
            .whereIn("fromId", nodeIds)
            .get()
            .await()

        val edgeTo = edgeCollection
            .whereIn("toId", nodeIds)
            .get()
            .await()

        (edgeFrom.documents + edgeTo.documents)
            .distinctBy { it.id }
            .forEach { doc ->
                batch.delete(doc.reference)
            }

        batch.commit().await()
    }

}