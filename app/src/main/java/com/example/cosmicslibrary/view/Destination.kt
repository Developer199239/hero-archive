package com.example.cosmicslibrary.view

sealed class Destination(val route: String, val label: String) {
    object Library : Destination("library", "Library")
    object Collection : Destination("collection", "Collection")
    object CharacterDetails : Destination("character/{characterId}", "Details") {
        fun createRoute(characterId: Int) = "character/$characterId"
    }
}
