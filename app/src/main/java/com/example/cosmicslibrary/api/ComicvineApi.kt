package com.example.cosmicslibrary.api

import com.example.cosmicslibrary.model.CharacterApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicvineApi {
    @GET("search/")
    suspend fun searchCharacters(
        @Query("query") query: String,
        @Query("resources") resources: String = "character"
    ): CharacterApiResponse
}
