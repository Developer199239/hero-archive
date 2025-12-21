package com.example.cosmicslibrary

import com.example.cosmicslibrary.api.ApiService
import com.example.cosmicslibrary.api.ComicvineApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = ComicvineApiRepo(ApiService.api)
}