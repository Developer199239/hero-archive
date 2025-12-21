package com.example.cosmicslibrary.api

import com.example.cosmicslibrary.model.CharacterApiResponse
import kotlinx.coroutines.flow.MutableStateFlow

class ComicvineApiRepo(private val api: ComicvineApi) {
    val characters = MutableStateFlow<NetworkResult<CharacterApiResponse>>(NetworkResult.Initial())
    
    suspend fun query(query: String) {
        characters.value = NetworkResult.Loading()
        try {
            val response = api.searchCharacters(query)
            characters.value = NetworkResult.Success(response)
        } catch (e: Exception) {
            characters.value = NetworkResult.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }
}
