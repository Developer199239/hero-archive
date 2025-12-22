package com.example.cosmicslibrary.data.repository

import com.example.cosmicslibrary.data.local.CharacterDao
import com.example.cosmicslibrary.data.local.DbCharacter
import com.example.cosmicslibrary.data.local.DbNote
import com.example.cosmicslibrary.data.local.NoteDao
import com.example.cosmicslibrary.data.remote.ComicvineApi
import com.example.cosmicslibrary.domain.model.CharacterApiResponse
import com.example.cosmicslibrary.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: ComicvineApi,
    private val characterDao: CharacterDao,
    private val noteDao: NoteDao
) : CharacterRepository {

    override suspend fun searchCharacters(query: String): CharacterApiResponse =
        api.searchCharacters(query)

    override fun getCharactersFromRepo(): Flow<List<DbCharacter>> =
        characterDao.getAllCharacters()

    override suspend fun getCharacterFromRepo(characterId: Int): DbCharacter? =
        characterDao.getCharacterById(characterId)

    override suspend fun addCharacterToRepo(character: DbCharacter) =
        characterDao.addCharacter(character)

    override suspend fun updateCharacterInRepo(character: DbCharacter) =
        characterDao.updateCharacter(character)

    override suspend fun deleteCharacterFromRepo(character: DbCharacter) =
        characterDao.deleteCharacter(character)

    override fun getAllNotes(): Flow<List<DbNote>> = noteDao.getAllNotes()

    override fun getNotesFromRepo(characterId: Int): Flow<List<DbNote>> =
        noteDao.getNotes(characterId)

    override suspend fun addNoteToRepo(note: DbNote) = noteDao.insertNote(note)

    override suspend fun updateNoteInRepo(note: DbNote) = noteDao.updateNote(note)

    override suspend fun deleteNoteFromRepo(note: DbNote) = noteDao.deleteNote(note)

    override suspend fun deleteAllNotes(characterId: Int) = noteDao.deleteAllNotes(characterId)
}
