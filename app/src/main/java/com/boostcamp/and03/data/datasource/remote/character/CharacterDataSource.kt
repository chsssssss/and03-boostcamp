package com.boostcamp.and03.data.datasource.remote.character

import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.response.CharacterResponse

interface CharacterDataSource {
    suspend fun getCharacters(userId: String, bookId: String): List<CharacterResponse>

    suspend fun addCharacter(userId: String, bookId: String, character: CharacterRequest)

    suspend fun deleteCharacter(userId: String, bookId: String, characterId: String)
}