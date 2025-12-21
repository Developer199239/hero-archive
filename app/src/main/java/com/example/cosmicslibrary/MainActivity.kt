package com.example.cosmicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.cosmicslibrary.view.CharactersBottomNav
import com.example.cosmicslibrary.view.CollectionScreen
import com.example.cosmicslibrary.view.Destination
import com.example.cosmicslibrary.view.LibraryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CosmicsLibraryTheme {
                val navController = rememberNavController()
                CharactersScaffold(navController = navController)
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController) {
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
                LibraryScreen()
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetails.route) { navBackStackEntry ->
                val characterId = navBackStackEntry.arguments?.getString("characterId")
                // CharacterDetailsScreen(characterId)
            }
        }
    }
}
