package com.example.cosmicslibrary.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cosmicslibrary.domain.model.Character
import com.example.cosmicslibrary.domain.model.CharacterApiResponse
import com.example.cosmicslibrary.presentation.viewmodel.LibraryApiViewModel
import com.example.cosmicslibrary.util.NetworkResult
import com.example.cosmicslibrary.util.connectivity.ConnectivityObservable

@Composable
fun LibraryScreen(
    navController: NavController,
    vm: LibraryApiViewModel = hiltViewModel()
) {
    val result by vm.result.collectAsState()
    val queryText by vm.queryText.collectAsState()
    val networkStatus by vm.networkAvailable.observe().collectAsState(ConnectivityObservable.Status.Available)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (networkStatus == ConnectivityObservable.Status.Unavailable || networkStatus == ConnectivityObservable.Status.Lost) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Network unavailable",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        OutlinedTextField(
            value = queryText,
            onValueChange = vm::onQueryUpdate,
            label = { Text("Character search") },
            placeholder = { Text(text = "Character") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (val networkResult = result) {
                is NetworkResult.Initial -> {
                    Text("Search for a character", modifier = Modifier.padding(top = 20.dp))
                }
                is NetworkResult.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
                }
                is NetworkResult.Success -> {
                    ShowCharactersList(networkResult.data, navController)
                }
                is NetworkResult.Error -> {
                    Text(text = "Error: ${networkResult.message}", color = Color.Red, modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
}

@Composable
fun ShowCharactersList(
    response: CharacterApiResponse,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(response.results) { character ->
            CharacterItem(character = character) {
                navController.navigate(Destination.CharacterDetails.createRoute(character.id))
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun CharacterItem(character: Character, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.image.smallUrl,
            contentDescription = character.name,
            modifier = Modifier.size(80.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = character.name, fontWeight = FontWeight.Bold)
            Text(text = character.realName ?: "")
        }
    }
}
