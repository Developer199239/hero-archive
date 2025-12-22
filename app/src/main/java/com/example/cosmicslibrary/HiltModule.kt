package com.example.cosmicslibrary

import android.content.Context
import androidx.room.Room
import com.example.cosmicslibrary.api.ApiService
import com.example.cosmicslibrary.api.ComicvineApiRepo
import com.example.cosmicslibrary.model.db.CharacterDao
import com.example.cosmicslibrary.model.db.CollectionDb
import com.example.cosmicslibrary.model.db.CollectionDbRepoImpl
import com.example.cosmicslibrary.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = ComicvineApiRepo(ApiService.api)

    @Provides
    fun providerCollectionDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao) =
        CollectionDbRepoImpl(characterDao)
}