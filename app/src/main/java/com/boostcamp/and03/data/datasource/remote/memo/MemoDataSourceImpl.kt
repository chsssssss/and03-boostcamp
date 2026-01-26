package com.boostcamp.and03.data.datasource.remote.memo

import android.util.Log
import com.boostcamp.and03.data.mapper.MemoResponseMapper
import com.boostcamp.and03.data.model.request.CanvasMemoRequest
import com.boostcamp.and03.data.model.request.TextMemoRequest
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.MemoResponse
import com.boostcamp.and03.data.model.response.memo.TextMemoResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MemoDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : MemoDataSource {
    override fun getMemos(
        userId: String,
        bookId: String
    ): Flow<List<MemoResponse>> = callbackFlow {
        val registration = db.collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("memo")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val memos = snapshot.documents.mapNotNull { document ->
                        val data = document.data ?: return@mapNotNull null
                        MemoResponseMapper.fromFirebase(document.id, data)
                    }

                    trySend(memos)
                }
            }
        awaitClose { registration.remove() }
    }

    override suspend fun addTextMemo(
        userId: String,
        bookId: String,
        memo: TextMemoRequest
    ) {
        try {
            val data = hashMapOf(
                "title" to memo.title,
                "content" to memo.content,
                "type" to memo.type,
                "startPage" to memo.startPage,
                "endPage" to memo.endPage,
                "createdAt" to FieldValue.serverTimestamp(),
                "updateTime" to FieldValue.serverTimestamp()
            )

            val collectionRef = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")

            val newDocRef = collectionRef.document()
            newDocRef.set(data).await()

            Log.d("MemoDataSourceImpl", "Memo added: ${newDocRef.id}")
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "Failed to add memo: ${e.message}")
            throw e
        }
    }

    override suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memo: CanvasMemoRequest
    ) {
        try {
            val data = hashMapOf(
                "title" to memo.title,
                "type" to memo.type,
                "startPage" to memo.startPage,
                "endPage" to memo.endPage,
                "createdAt" to FieldValue.serverTimestamp(),
                "updateTime" to FieldValue.serverTimestamp()
            )

            val collectionRef = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")

            val newDocRef = collectionRef.document()
            newDocRef.set(data).await()

            Log.d("MemoDataSourceImpl", "Memo added: ${newDocRef.id}")
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "Failed to add memo: ${e.message}")
            throw e
        }
    }

    override suspend fun updateTextMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: TextMemoRequest
    ) {
        try {
            val data = hashMapOf(
                "title" to memo.title,
                "content" to memo.content,
                "type" to memo.type,
                "startPage" to memo.startPage,
                "endPage" to memo.endPage,
                "updateTime" to FieldValue.serverTimestamp()
            )

            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)
                .update(data)
                .await()
            Log.d("MemoDataSourceImpl", "Memo updated: $memoId")
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "Failed to update memo: ${e.message}")
            throw e
        }
    }

    override suspend fun updateCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: CanvasMemoRequest
    ) {
        try {
            val data = hashMapOf(
                "title" to memo.title,
                "type" to memo.type,
                "startPage" to memo.startPage,
                "endPage" to memo.endPage,
                "updateTime" to FieldValue.serverTimestamp()
            )

            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)
                .update(data)
                .await()

            Log.d("MemoDataSourceImpl", "Memo updated: $memoId")
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "Failed to update memo: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteMemo(
        userId: String,
        bookId: String,
        memoId: String
    ) {
        try {
            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)
                .delete()
                .await()

            Log.d("MemoDataSourceImpl", "Memo deleted: $memoId")
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "Failed to delete memo: ${e.message}")
            throw e
        }
    }

    override suspend fun getTextMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): TextMemoResponse {
        return try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("memo")
                .document(memoId)
                .get()
                .await()

            if (!snapshot.exists()) {
                throw IllegalStateException("Memo not found: $memoId")
            }

            val data = snapshot.data
                ?: throw IllegalStateException("Memo data is null: $memoId")

            TextMemoResponse(
                id = snapshot.id,
                title = data["title"] as? String ?: "",
                content = data["content"] as? String ?: "",
                createdAt = data["createdAt"] as? String ?: "",
                type = data["type"] as? String ?: "TEXT",
                startPage = (data["startPage"] as? Long)?.toInt() ?: 0,
                endPage = (data["endPage"] as? Long)?.toInt() ?: 0
            )
        } catch (e: Exception) {
            Log.e("MemoDataSourceImpl", "getTextMemo error: ${e.message}", e)
            throw e
        }
    }

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
}