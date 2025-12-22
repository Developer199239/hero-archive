package com.example.cosmicslibrary.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {
    fun getCharactersFromRepo(): Flow<List<DbCharacter>>
    suspend fun getCharacterFromRepo(characterId: Int): DbCharacter?
    suspend fun addCharacterToRepo(character: DbCharacter)
    suspend fun updateCharacterInRepo(character: DbCharacter)
    suspend fun deleteCharacterFromRepo(character: DbCharacter)
}
