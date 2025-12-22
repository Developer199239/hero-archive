package com.example.cosmicslibrary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id")
    fun getAllNotes(): Flow<List<DbNote>>

    @Query("SELECT * FROM note_table WHERE characterId = :characterId ORDER BY id ASC")
    fun getNotes(characterId: Int): Flow<List<DbNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: DbNote)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: DbNote)

    @Delete
    suspend fun deleteNote(note: DbNote)

    @Query("DELETE FROM note_table WHERE characterId = :characterId")
    suspend fun deleteAllNotes(characterId: Int)
}
