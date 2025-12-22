package com.example.cosmicslibrary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cosmicslibrary.domain.model.Character
import com.example.cosmicslibrary.data.local.Constants.CHARACTER_TABLE

@Entity(tableName = CHARACTER_TABLE)
data class DbCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val realName: String?,
    val aliases: String?,
    val imageUrl: String?,
    val deck: String?,
    val description: String?,
    val publisherName: String?,
    val originName: String?
)

fun Character.toDbCharacter(): DbCharacter {
    return DbCharacter(
        id = id,
        name = name,
        realName = realName,
        aliases = aliases,
        imageUrl = image.smallUrl,
        deck = deck,
        description = description,
        publisherName = publisher?.name,
        originName = origin?.name
    )
}
