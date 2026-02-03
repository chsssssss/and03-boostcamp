package com.boostcamp.and03.data.datasource.remote.canvasmemo

import android.util.Log
import com.boostcamp.and03.data.mapper.MemoEdgeMapper
import com.boostcamp.and03.data.mapper.MemoNodeMapper
import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.EdgeResponse
import com.boostcamp.and03.data.model.response.memo.NodeResponse
import com.boostcamp.and03.data.model.response.memo.GraphResponse
import com.google.firebase.firestore.DocumentReference
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


    override suspend fun saveCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        graph: GraphRequest
    ) {
        try {
            // memo 문서 참조
            val collectionRef = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)

            val batch = db.batch()

            batch.set(
                collectionRef,
                mapOf("updatedTime" to FieldValue.serverTimestamp()),
                SetOptions.merge()
            )

            val nodeCollection = collectionRef.collection("node")
            val edgeCollection = collectionRef.collection("edge")

            // 기존 DB 상태 조회
            val existingNodeIds = nodeCollection
                .get()
                .await()
                .documents
                .map { it.id }
                .toSet()

            val existingEdgeIds = edgeCollection
                .get()
                .await()
                .documents
                .map { it.id }
                .toSet()

            // 현재 graph에 포함된 ID 목록
            val graphNodeIds = graph.nodes.map { it.id }.toSet()
            val graphEdgeIds = graph.edges.map { it.id }.toSet()

            // 삭제 대상 계산
            val nodesToDelete = existingNodeIds - graphNodeIds
            val edgesToDelete = existingEdgeIds - graphEdgeIds

            // 삭제 반영
            nodesToDelete.forEach { nodeId ->
                batch.delete(nodeCollection.document(nodeId))
            }

            edgesToDelete.forEach { edgeId ->
                batch.delete(edgeCollection.document(edgeId))
            }

            // graph 기준 업데이트
            graph.nodes.forEach { node ->
                val newNodeRef = nodeCollection.document(node.id)
                batch.set(newNodeRef, node)
            }

            graph.edges.forEach { edge ->
                val newEdgeRef = edgeCollection.document(edge.id)
                batch.set(newEdgeRef, edge)
            }

            // 커밋
            batch.commit().await()

            Log.d("CanvasMemoDataSourceImpl", "CanvasMemo added: ${collectionRef.id}")
        } catch (e: Exception) {
            Log.e("CanvasMemoDataSourceImpl", "Failed to add canvas memo: ${e.message}")
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
    ): DocumentReference {
        return db
            .collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .document(memoId)
    }
}