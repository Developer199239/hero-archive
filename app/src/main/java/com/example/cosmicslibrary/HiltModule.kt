package com.example.cosmicslibrary

import android.content.Context
import androidx.room.Room
import com.example.cosmicslibrary.api.ApiService
import com.example.cosmicslibrary.api.ComicvineApiRepo
import com.example.cosmicslibrary.model.db.CharacterDao
import com.example.cosmicslibrary.model.db.CollectionDb
import com.example.cosmicslibrary.model.db.CollectionDbRepo
import com.example.cosmicslibrary.model.db.CollectionDbRepoImpl
import com.example.cosmicslibrary.model.db.Constants.DB
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
    fun provideApiRepo() = ComicvineApiRepo(ApiService.api)

    @Provides
    @Singleton
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    @Singleton
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    @Singleton
    fun provideCollectionDbRepo(characterDao: CharacterDao): CollectionDbRepo =
        CollectionDbRepoImpl(characterDao)
}
