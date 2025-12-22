package com.example.cosmicslibrary.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun getAllCharacters(): Flow<List<DbCharacter>>

    @Query("SELECT * FROM character_table WHERE id = :id")
    suspend fun getCharacterById(id: Int): DbCharacter?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(character: DbCharacter)

    @Update
    suspend fun updateCharacter(character: DbCharacter)

    @Delete
    suspend fun deleteCharacter(character: DbCharacter)
}
