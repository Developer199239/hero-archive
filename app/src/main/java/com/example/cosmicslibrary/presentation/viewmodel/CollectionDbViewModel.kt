package com.example.cosmicslibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicslibrary.data.local.DbCharacter
import com.example.cosmicslibrary.data.local.DbNote
import com.example.cosmicslibrary.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDbViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _currentCharacter = MutableStateFlow<DbCharacter?>(null)
    val currentCharacter: StateFlow<DbCharacter?> = _currentCharacter.asStateFlow()

    private val _collection = MutableStateFlow<List<DbCharacter>>(listOf())
    val collection: StateFlow<List<DbCharacter>> = _collection.asStateFlow()

    private val _notes = MutableStateFlow<List<DbNote>>(listOf())
    val notes: StateFlow<List<DbNote>> = _notes.asStateFlow()

    init {
        getCollection()
        getNotes()
    }

    private fun getCollection() {
        viewModelScope.launch {
            repository.getCharactersFromRepo().collect {
                _collection.value = it
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    fun setCurrentCharacterId(characterId: Int?) {
        characterId?.let { id ->
            viewModelScope.launch {
                _currentCharacter.value = repository.getCharacterFromRepo(id)
            }
        }
    }

    fun addCharacter(character: DbCharacter) {
        viewModelScope.launch {
            repository.addCharacterToRepo(character)
        }
    }

    fun deleteCharacter(character: DbCharacter) {
        viewModelScope.launch {
            repository.deleteAllNotes(character.id)
            repository.deleteCharacterFromRepo(character)
        }
    }

    fun addNote(note: DbNote) {
        viewModelScope.launch {
            repository.addNoteToRepo(note)
        }
    }

    fun deleteNote(note: DbNote) {
        viewModelScope.launch {
            repository.deleteNoteFromRepo(note)
        }
    }
}
