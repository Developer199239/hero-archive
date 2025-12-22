package com.example.cosmicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicslibrary.model.db.CollectionDbRepo
import com.example.cosmicslibrary.model.db.DbCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDbViewModel @Inject constructor(private val repo: CollectionDbRepo) : ViewModel() {
    val currentCharacter = MutableStateFlow<DbCharacter?>(null)
    val collection = MutableStateFlow<List<DbCharacter>>(listOf())

    init {
        getCollection()
    }

    private fun getCollection() {
        viewModelScope.launch {
            repo.getCharactersFromRepo().collect {
                collection.value = it
            }
        }
    }

    fun setCurrentCharacterId(characterId: Int?) {
        characterId?.let { id ->
            viewModelScope.launch {
                // Since this is now a suspend function returning an object, 
                // just assign it directly instead of using .collect{}
                currentCharacter.value = repo.getCharacterFromRepo(id)
            }
        }
    }

    fun addCharacter(character: DbCharacter) {
        viewModelScope.launch {
            repo.addCharacterToRepo(character)
        }
    }

    fun deleteCharacter(character: DbCharacter) {
        viewModelScope.launch {
            repo.deleteCharacterFromRepo(character)
        }
    }
}
