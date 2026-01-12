package com.boostcamp.and03.data.datasource.remote.character

import com.boostcamp.and03.data.model.response.CharacterResponse

interface CharacterDataSource {
    suspend fun getCharacters(userId: String, bookId: String): List<CharacterResponse>
}