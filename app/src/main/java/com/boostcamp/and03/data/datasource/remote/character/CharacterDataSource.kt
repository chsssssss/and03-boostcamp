package com.boostcamp.and03.data.datasource.remote.character

import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.response.CharacterResponse

interface CharacterDataSource {
    suspend fun getCharacters(
        userId: String,
        bookId: String
    ): List<CharacterResponse>

    suspend fun getCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ): CharacterResponse

    suspend fun addCharacter(
        userId: String,
        bookId: String,
        character: CharacterRequest
    )

    suspend fun deleteCharacter(
        userId: String,
        bookId: String,
        characterId: String
    )

    suspend fun updateCharacter(
        userId: String,
        bookId: String,
        characterId: String,
        character: CharacterRequest
    )
}