package com.boostcamp.and03.data.datasource.remote

import com.boostcamp.and03.data.model.request.BookEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreBookRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : BookRemoteDataSource {

    override suspend fun saveBook(
        userId: String,
        book: BookEntity
    ) {
        firestore
            .collection("user")
            .document(userId)
            .collection("book")
            .document(book.isbn)
            .set(book)
            .await()
    }

    override suspend fun loadSavedBooks(
        userId: String
    ): List<BookEntity> = firestore.collection("user")
        .document(userId)
        .collection("book")
        .get()
        .await()
        .documents
        .mapNotNull { it.toObject(BookEntity::class.java) }
}