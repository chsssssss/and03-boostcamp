package com.boostcamp.and03.data.datasource.remote.bookstorage

import android.util.Log
import com.boostcamp.and03.data.model.request.BookStorageRequest
import com.boostcamp.and03.data.model.response.BookDetailResponse
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookStorageDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : BookStorageDataSource {
    override suspend fun getBooks(userId: String): List<BookStorageResponse> {
        return try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .get()
                .await()

            @Suppress("UNCHECKED_CAST")
            snapshot.documents.mapNotNull { document ->
                document.data?.let { data ->
                    BookStorageResponse(
                        id = document.id,
                        title = data["title"] as? String ?: "",
                        author = data["author"] as? List<String> ?: emptyList(),
                        publisher = data["publisher"] as? String ?: "",
                        isbn = data["isbn"] as? String ?: "",
                        thumbnail = data["thumbnail"] as? String ?: "",
                        totalPage = (data["totalPage"] as? Long)?.toInt() ?: 0
                    )
                }
            }

        } catch (e: Exception) {
            Log.e("BookStorage", "Error: ${e.message}")
            throw e
        }
    }

    override suspend fun getBookDetail(
        userId: String,
        bookId: String
    ): BookDetailResponse? {
        return try {
            val document = db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .get()
                .await()

            if (!document.exists()) {
                Log.w("BookStorage", "Book document does not exist")
                return null
            }

            val data = document.data ?: return null

            @Suppress("UNCHECKED_CAST")
            BookDetailResponse(
                id = document.id,
                title = data["title"] as? String ?: "",
                author = data["author"] as? List<String> ?: emptyList(),
                publisher = data["publisher"] as? String ?: "",
                thumbnail = data["thumbnail"] as? String ?: "",
                totalPage = (data["totalPage"] as? Long)?.toInt() ?: 0
            )
        } catch (e: Exception) {
            Log.e("BookStorage", "Error getting book detail", e)
            throw e
        }
    }


    override suspend fun saveBook(
        userId: String,
        book: BookStorageRequest
    ) {
        db.collection("user")
            .document(userId)
            .collection("book")
            .document()
            .set(book)
            .await()
    }
}
