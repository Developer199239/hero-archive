package com.example.cosmicslibrary.domain.repository

import com.example.cosmicslibrary.domain.model.CharacterApiResponse
import com.example.cosmicslibrary.data.local.DbCharacter
import com.example.cosmicslibrary.data.local.DbNote
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    // Remote
    suspend fun searchCharacters(query: String): CharacterApiResponse

    // Local Characters
    fun getCharactersFromRepo(): Flow<List<DbCharacter>>
    suspend fun getCharacterFromRepo(characterId: Int): DbCharacter?
    suspend fun addCharacterToRepo(character: DbCharacter)
    suspend fun updateCharacterInRepo(character: DbCharacter)
    suspend fun deleteCharacterFromRepo(character: DbCharacter)

    // Local Notes
    fun getAllNotes(): Flow<List<DbNote>>
    fun getNotesFromRepo(characterId: Int): Flow<List<DbNote>>
    suspend fun addNoteToRepo(note: DbNote)
    suspend fun updateNoteInRepo(note: DbNote)
    suspend fun deleteNoteFromRepo(note: DbNote)
    suspend fun deleteAllNotes(characterId: Int)
}
