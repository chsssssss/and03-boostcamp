package com.boostcamp.and03.data.datasource.remote.quote

import android.util.Log
import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.QuoteResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): QuoteDataSource {

    override fun getQuotes(
        userId: String,
        bookId: String
    ): Flow<List<QuoteResponse>> = callbackFlow {
        val registration = db.collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("quote")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val quotes = snapshot.documents.mapNotNull { document ->
                        document.data?.let { data ->
                            Log.d("QuoteDataSourceImpl", "data: $data")
                            QuoteResponse(
                                id = document.id,
                                content = data["content"] as? String ?: "",
                                page = (data["page"] as? Long)?.toInt() ?: 0,
                                createdAt = data["createdAt"] as? String ?: "",
                            )
                        }
                    }
                    trySend(quotes)
                }
            }
        awaitClose { registration.remove() }
    }

    override suspend fun getQuote(
        userId: String,
        bookId: String,
        quoteId: String
    ): QuoteResponse {
        return try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("quote")
                .document(quoteId)
                .get()
                .await()

            if (!snapshot.exists()) {
                throw IllegalStateException("Quote not found: $quoteId")
            }

            val data = snapshot.data
                ?: throw IllegalStateException("Quote data is null: $quoteId")

            QuoteResponse(
                id = snapshot.id,
                content = data["content"] as? String ?: "",
                page = (data["page"] as? Long)
                    ?.coerceIn(
                        Int.MIN_VALUE.toLong(),
                        Int.MAX_VALUE.toLong()
                    )
                    ?.toInt()
                    ?: 0,
                createdAt = data["createdAt"] as? String ?: ""
            )
        } catch (e: Exception) {
            Log.e("QuoteDataSourceImpl", "Failed to get quote: ${e.message}")
            throw e
        }
    }

    override suspend fun addQuote(
        userId: String,
        bookId: String,
        quote: QuoteRequest
    ) {
        try {
            val collectionRef = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("quote")

            val newDocRef = collectionRef.document()

            val data = mapOf(
                "content" to quote.content,
                "page" to quote.page,
                "createdAt" to FieldValue.serverTimestamp()
            )
            newDocRef.set(data).await()

            Log.d("QuoteDataSourceImpl", "Quote added: ${newDocRef.id}")
        } catch (e: Exception) {
            Log.e("QuoteDataSourceImpl", "Failed to add quote: ${e.message}")
            throw e
        }
    }

    override suspend fun updateQuote(
        userId: String,
        bookId: String,
        quoteId: String,
        quote: QuoteRequest
    ) {
        try {
            val data = mapOf(
                "content" to quote.content,
                "page" to quote.page,
                "updatedAt" to FieldValue.serverTimestamp()
            )

            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("quote")
                .document(quoteId)
                .update(data)
                .await()

            Log.d("QuoteDataSourceImpl", "Quote updated: $quoteId")
        } catch (e: Exception) {
            Log.e("QuoteDataSourceImpl", "Failed to update quote: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteQuote(
        userId: String,
        bookId: String,
        quoteId: String
    ) {
        try {
            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("quote")
                .document(quoteId)
                .delete()
                .await()

            Log.d("QuoteDataSourceImpl", "Quote deleted: $quoteId")
        } catch (e: Exception) {
            Log.e("QuoteDataSourceImpl", "Failed to delete quote: ${e.message}")
            throw e
        }
    }
}