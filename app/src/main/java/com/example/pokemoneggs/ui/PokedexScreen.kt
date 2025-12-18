@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pokemoneggs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoneggs.GameViewModel

@Composable
fun PokedexScreen(vm: GameViewModel, onBack: () -> Unit) {
    val pokemon by vm.pokemon.collectAsState()
    val dexSet = pokemon.map { it.dex }.toSet()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokedex") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dexSet.sorted()) { dex ->
                Card {
                    Text(
                        text = "Pokémon Dex #$dex",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
