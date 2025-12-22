package com.example.cosmicslibrary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cosmicslibrary.model.Note
import com.example.cosmicslibrary.model.db.DbNote
import com.example.cosmicslibrary.viewmodel.CollectionDbViewModel

@Composable
fun CollectionScreen(
    navController: NavHostController,
    cvm: CollectionDbViewModel = hiltViewModel()
) {

    val charactersInCollection by cvm.collection.collectAsState()
    var expandedElementId by remember { mutableStateOf(-1) }
    val notes by cvm.notes.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(charactersInCollection) { character ->
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(4.dp)
                    .clickable {
                        expandedElementId = if (expandedElementId == character.id) -1 else character.id
                    }) {
                    AsyncImage(
                        model = character.imageUrl,
                        contentDescription = character.name,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                            .size(80.dp),
                        contentScale = ContentScale.FillHeight
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = character.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            maxLines = 2
                        )
                        Text(text = character.realName ?: "", fontStyle = FontStyle.Italic)
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                cvm.deleteCharacter(character)
                            },
                            tint = Color.Red
                        )

                        if (character.id == expandedElementId)
                            Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = null)
                        else
                            Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
                    }
                }
            }

            if (character.id == expandedElementId) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .padding(8.dp)
                ) {
                    val filteredNotes = notes.filter { it.characterId == character.id }
                    NotesList(filteredNotes, cvm)
                    CreateNoteForm(character.id, cvm)
                    
                    Button(
                        onClick = {
                            navController.navigate(Destination.CharacterDetails.createRoute(character.id))
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("View Details")
                    }
                }
            }

            HorizontalDivider(
                color = Color.LightGray,
                modifier = Modifier.padding(
                    top = 4.dp, bottom = 4.dp, start = 20.dp, end = 20.dp
                )
            )
        }
    }
}

@Composable
fun NotesList(notes: List<DbNote>, cvm: CollectionDbViewModel) {
    for (note in notes) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.4f))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, fontWeight = FontWeight.Bold)
                Text(text = note.text)
            }
            Icon(
                Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.clickable {
                    cvm.deleteNote(note)
                },
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun CreateNoteForm(characterId: Int, cvm: CollectionDbViewModel) {
    var isAddingNote by remember { mutableStateOf(false) }
    var newNoteTitle by remember { mutableStateOf("") }
    var newNoteText by remember { mutableStateOf("") }

    if (isAddingNote) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.4f))
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Add note", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            OutlinedTextField(
                value = newNoteTitle,
                onValueChange = { newNoteTitle = it },
                label = { Text(text = "Note title") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            OutlinedTextField(
                value = newNoteText,
                onValueChange = { newNoteText = it },
                label = { Text(text = "Note content") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { isAddingNote = false },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Cancel")
                }
                Button(onClick = {
                    if (newNoteTitle.isNotBlank() && newNoteText.isNotBlank()) {
                        val note = Note(characterId, newNoteTitle, newNoteText)
                        cvm.addNote(DbNote.fromNote(note))
                        newNoteTitle = ""
                        newNoteText = ""
                        isAddingNote = false
                    }
                }) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text("Save", modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    } else {
        Button(
            onClick = { isAddingNote = true },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text("Add Note", modifier = Modifier.padding(start = 4.dp))
        }
    }
}
