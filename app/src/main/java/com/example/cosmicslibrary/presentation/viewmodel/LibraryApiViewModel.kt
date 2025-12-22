package com.example.cosmicslibrary.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicslibrary.domain.model.Character
import com.example.cosmicslibrary.domain.model.CharacterApiResponse
import com.example.cosmicslibrary.domain.repository.CharacterRepository
import com.example.cosmicslibrary.util.connectivity.ConnectivityMonitor
import com.example.cosmicslibrary.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryApiViewModel @Inject constructor(
    private val repository: CharacterRepository,
    connectivityMonitor: ConnectivityMonitor
) : ViewModel() {

    private val _result = MutableStateFlow<NetworkResult<CharacterApiResponse>>(NetworkResult.Initial())
    val result: StateFlow<NetworkResult<CharacterApiResponse>> = _result.asStateFlow()

    private val _queryText = MutableStateFlow("")
    val queryText: StateFlow<String> = _queryText.asStateFlow()

    private val queryInput = Channel<String>(Channel.CONFLATED)

    val characterDetails: MutableState<Character?> = mutableStateOf(null)
    val networkAvailable = connectivityMonitor

    init {
        retrieveCharacters()
    }

    private fun retrieveCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { it.length >= 2 }
                .debounce(1000)
                .collect { query ->
                    _result.value = NetworkResult.Loading()
                    try {
                        val response = repository.searchCharacters(query)
                        _result.value = NetworkResult.Success(response)
                    } catch (e: Exception) {
                        _result.value = NetworkResult.Error(e.localizedMessage ?: "Unknown error")
                    }
                }
        }
    }

    fun onQueryUpdate(input: String) {
        _queryText.value = input
        queryInput.trySend(input)
    }

    fun getSingleCharacter(id: Int) {
        val currentResult = _result.value
        if (currentResult is NetworkResult.Success) {
            characterDetails.value = currentResult.data.results.find { it.id == id }
        }
    }
}
