package com.example.cosmicslibrary.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cosmicslibrary.model.db.toDbCharacter
import com.example.cosmicslibrary.viewmodel.CollectionDbViewModel
import com.example.cosmicslibrary.viewmodel.LibraryApiViewModel

@Composable
fun CharacterDetailsScreen(
    characterId: Int?,
    navController: NavHostController,
    lvm: LibraryApiViewModel = viewModel(),
    cvm: CollectionDbViewModel = viewModel()
) {
    val character by lvm.characterDetails
    val collection by cvm.collection.collectAsState()
    
    // Check if character is in collection by comparing IDs
    val inCollection = collection.any { it.id == characterId }

    LaunchedEffect(characterId) {
        if (characterId != null) {
            lvm.getSingleCharacter(characterId)
            cvm.setCurrentCharacterId(characterId)
        }
    }

    if (character == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text("Loading character details...")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = character?.image?.mediumUrl,
                contentDescription = character?.name,
                modifier = Modifier
                    .size(250.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = character?.name ?: "No name",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(4.dp)
            )

            character?.realName?.let {
                if (it.isNotBlank()) {
                    Text(
                        text = "Real Name: $it",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            character?.publisher?.let {
                Text(
                    text = "Publisher: ${it.name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Text(
                text = character?.deck ?: character?.description ?: "No description available",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            Button(
                onClick = {
                    if (!inCollection && character != null) {
                        cvm.addCharacter(character!!.toDbCharacter())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                if (!inCollection) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(text = "Add to collection", modifier = Modifier.padding(start = 8.dp))
                } else {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text(text = "In Collection", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}
