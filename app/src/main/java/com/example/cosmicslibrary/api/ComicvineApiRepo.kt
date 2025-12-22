package com.example.cosmicslibrary.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.cosmicslibrary.model.Character
import com.example.cosmicslibrary.model.CharacterApiResponse
import kotlinx.coroutines.flow.MutableStateFlow

class ComicvineApiRepo(private val api: ComicvineApi) {
    val characters = MutableStateFlow<NetworkResult<CharacterApiResponse>>(NetworkResult.Initial())
    val characterDetails: MutableState<Character?> = mutableStateOf(null)

    suspend fun query(query: String) {
        characters.value = NetworkResult.Loading()
        try {
            val response = api.searchCharacters(query)
            characters.value = NetworkResult.Success(response)
        } catch (e: Exception) {
            characters.value = NetworkResult.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    fun getSingleCharacter(id: Int) {
        val currentResult = characters.value
        if (currentResult is NetworkResult.Success) {
            characterDetails.value = currentResult.data.results.find { it.id == id }
        }
    }
}
