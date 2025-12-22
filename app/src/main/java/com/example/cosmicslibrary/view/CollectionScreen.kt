package com.example.cosmicslibrary.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cosmicslibrary.viewmodel.CollectionDbViewModel

@Composable
fun CollectionScreen(
    navController: NavHostController,
    cvm: CollectionDbViewModel = viewModel()
) {
    val charactersInCollection by cvm.collection.collectAsState()
    var expandedElementId by remember { mutableStateOf(-1) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(charactersInCollection) { character ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Destination.CharacterDetails.createRoute(character.id))
                    }
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = character.imageUrl,
                        contentDescription = character.name,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = character.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            maxLines = 1
                        )
                        character.realName?.let {
                            if (it.isNotBlank()) {
                                Text(
                                    text = it,
                                    fontStyle = FontStyle.Italic,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        character.publisherName?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .clickable { cvm.deleteCharacter(character) }
                                .padding(8.dp),
                            tint = Color.Red
                        )

                        Icon(
                            imageVector = if (expandedElementId == character.id) 
                                Icons.Outlined.KeyboardArrowUp 
                            else 
                                Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Expand",
                            modifier = Modifier.clickable {
                                expandedElementId = if (expandedElementId == character.id) -1 else character.id
                            }
                        )
                    }
                }

                AnimatedVisibility(visible = expandedElementId == character.id) {
                    Text(
                        text = character.deck ?: "No details available",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
            }
        }
    }
}
