package com.example.cosmicslibrary.api

import com.example.cosmicslibrary.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://comicvine.gamespot.com/api/"

    private val clientInterceptor = Interceptor { chain ->
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.COMICVINE_API_KEY)
            .addQueryParameter("format", "json")
            .build()
        
        val request = original.newBuilder()
            .url(url)
            .build()
        
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(clientInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: ComicvineApi = retrofit.create(ComicvineApi::class.java)
}
