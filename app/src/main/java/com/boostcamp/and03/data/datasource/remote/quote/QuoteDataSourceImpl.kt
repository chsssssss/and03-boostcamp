package com.boostcamp.and03.data.datasource.remote.quote

import android.util.Log
import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.response.QuoteResponse
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class QuoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): QuoteDataSource {
    override suspend fun getQuotes(
        userId: String,
        bookId: String
    ): List<QuoteResponse> {
        return try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("quote")
                .get()
                .await()

            snapshot.documents.map { document ->
                val docId = document.id
                val data = document.data
                Log.d("QuoteDataSourceImpl", "data: $data")
                if (data != null) {
                    QuoteResponse(
                        id = docId,
                        content = data["content"] as? String ?: "",
                        page = data["page"] as? Int ?: 0,
                        createdAt = data["createdAt"] as? String ?: "",
                    )
                }
                else {
                    throw Exception("data is null")
                }
            }
        } catch (e: Exception) {
            Log.e("BookStorage", "Error: ${e.message}")
            emptyList()
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
            newDocRef.set(quote).await()

            Log.d("QuoteDataSourceImpl", "Quote added: ${newDocRef.id}")
        } catch (e: Exception) {
            Log.e("QuoteDataSourceImpl", "Failed to add quote: ${e.message}")
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