package com.example.cosmicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val characterDao: CharacterDao) : CollectionDbRepo {
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
}
