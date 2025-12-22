package com.example.cosmicslibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cosmicslibrary.ui.theme.CosmicsLibraryTheme
import com.example.cosmicslibrary.view.CharacterDetailsScreen
import com.example.cosmicslibrary.view.CharactersBottomNav
import com.example.cosmicslibrary.view.CollectionScreen
import com.example.cosmicslibrary.view.Destination
import com.example.cosmicslibrary.view.LibraryScreen
import com.example.cosmicslibrary.viewmodel.CollectionDbViewModel
import com.example.cosmicslibrary.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val lvm by viewModels<LibraryApiViewModel>()
    private val cvm by viewModels<CollectionDbViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CosmicsLibraryTheme {
                val navController = rememberNavController()
                CharactersScaffold(navController = navController, lvm, cvm)
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController, lvm: LibraryApiViewModel, cvm: CollectionDbViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CharactersBottomNav(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Library.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Library.route) {
                LibraryScreen(navController = navController, lvm)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetails.route) { navBackStackEntry ->
                val characterIdString = navBackStackEntry.arguments?.getString("characterId")
                Log.d("=====>", "Character ID: $characterIdString")
                val characterId = characterIdString?.toIntOrNull()
                CharacterDetailsScreen(characterId, navController, lvm, cvm)
            }
        }
    }
}
