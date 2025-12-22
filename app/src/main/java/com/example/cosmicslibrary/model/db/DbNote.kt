package com.example.cosmicslibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cosmicslibrary.model.Note

@Entity(tableName = Constants.NOTE_TABLE)
data class DbNote(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var characterId: Int,
    var title: String,
    var text: String
) {
    companion object{
        fun fromNote(note: Note) = DbNote(
            id = 0,
            characterId = note.characterId,
            title = note.title,
            text = note.text
        )
    }
}