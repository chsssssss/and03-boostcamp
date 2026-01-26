package com.boostcamp.and03.data.datasource.remote.character

import android.util.Log
import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CharacterDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CharacterDataSource {
    override suspend fun getCharacters(
        userId: String,
        bookId: String
    ): Flow<List<CharacterResponse>> = callbackFlow {
        val registration = db.collection("user")
            .document(userId)
            .collection("book")
            .document(bookId)
            .collection("character")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val characters = snapshot.documents.mapNotNull { document ->
                        document.data?.let { data ->
                            CharacterResponse(
                                id = document.id,
                                role = data["role"] as? String ?: "",
                                description = data["description"] as? String ?: "",
                                name = data["name"] as? String ?: "",
                            )
                        }
                    }
                    trySend(characters)
                }
            }
        awaitClose { registration.remove() }
    }

    override suspend fun getCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ): CharacterResponse {
        return try {
            val snapshot = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("character")
                .document(characterId)
                .get()
                .await()

            if (!snapshot.exists()) {
                throw IllegalStateException("Character not found: $characterId")
            }

            val data = snapshot.data
                ?: throw IllegalStateException("Character data is null: $characterId")

            CharacterResponse(
                id = snapshot.id,
                role = data["role"] as? String ?: "",
                description = data["description"] as? String ?: "",
                name = data["name"] as? String ?: "",
            )

        } catch (e: Exception) {
            Log.e("CharacterDataSourceImpl", "Failed to get character: ${e.message}")
            throw e
        }
    }

    override suspend fun addCharacter(
        userId: String,
        bookId: String,
        character: CharacterRequest
    ) {
        try {
            val collectionRef = db
                .collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("character")

            val newDocRef = collectionRef.document()
            newDocRef.set(character).await()

            Log.d("CharacterDataSourceImpl", "Character added: ${newDocRef.id}")
        } catch (e: Exception) {
            Log.e("CharacterDataSourceImpl", "Failed to add character: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ) {
        try {
            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("character")
                .document(characterId)
                .delete()
                .await()

            Log.d("CharacterDataSourceImpl", "Character deleted: $characterId")
        } catch (e: Exception) {
            Log.e("CharacterDataSourceImpl", "Failed to delete character: ${e.message}")
            throw e
        }
    }

    override suspend fun updateCharacter(
        userId: String,
        bookId: String,
        characterId: String,
        character: CharacterRequest
    ) {
        try {
            val data: HashMap<String, Any> = hashMapOf(
                "role" to character.role,
                "description" to character.description,
                "name" to character.name,
            )

            db.collection("user")
                .document(userId)
                .collection("book")
                .document(bookId)
                .collection("character")
                .document(characterId)
                .update(data)
                .await()

            Log.d("CharacterDataSourceImpl", "Character updated: $characterId")
        } catch (e: Exception) {
            Log.e("CharacterDataSourceImpl", "Failed to update character: ${e.message}")
            throw e
        }
    }
}
