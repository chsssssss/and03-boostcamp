package com.boostcamp.and03.data.datasource.remote.book_storage

import android.util.Log
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookStorageDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): BookStorageDataSource {
    override suspend fun getBooks(userId: String): List<BookStorageResponse> {
        return try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .get()
                .await()


            snapshot.documents.map { document ->
                val docId = document.id
                val data = document.data
                Log.d("BookStorageDataSourceImpl", "data: $data")
                if (data != null) {
                    BookStorageResponse(
                        id = docId,
                        title = data["title"] as? String ?: "",
                        author = data["author"] as? String ?: "",
                        publisher = data["publisher"] as? String ?: "",
                        isbn = data["isbn"] as? String ?: "",
                        thumbnail = data["thumbnail"] as? String ?: "",
                        totalPage = (data["totalPage"] as? Long)?.toInt() ?: 0
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
}
