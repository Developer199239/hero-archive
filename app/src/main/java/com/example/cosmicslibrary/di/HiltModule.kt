package com.example.cosmicslibrary.di

import android.content.Context
import androidx.room.Room
import com.example.cosmicslibrary.data.local.CharacterDao
import com.example.cosmicslibrary.data.local.CollectionDb
import com.example.cosmicslibrary.data.local.Constants.DB
import com.example.cosmicslibrary.data.local.NoteDao
import com.example.cosmicslibrary.data.remote.ApiService
import com.example.cosmicslibrary.data.remote.ComicvineApi
import com.example.cosmicslibrary.data.repository.CharacterRepositoryImpl
import com.example.cosmicslibrary.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Provides
    @Singleton
    fun provideComicvineApi(): ComicvineApi = ApiService.api

    @Provides
    @Singleton
    fun provideCollectionDb(@ApplicationContext context: Context): CollectionDb =
        Room.databaseBuilder(context, CollectionDb::class.java, DB)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCharacterDao(collectionDb: CollectionDb): CharacterDao = collectionDb.characterDao()

    @Provides
    @Singleton
    fun provideNoteDao(collectionDb: CollectionDb): NoteDao = collectionDb.noteDao()

    @Provides
    @Singleton
    fun provideCharacterRepository(
        api: ComicvineApi,
        characterDao: CharacterDao,
        noteDao: NoteDao
    ): CharacterRepository = CharacterRepositoryImpl(api, characterDao, noteDao)
}
