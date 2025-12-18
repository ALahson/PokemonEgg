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
fun TeamScreen(vm: GameViewModel, onBack: () -> Unit) {
    val pokemon by vm.pokemon.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Team") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        if (pokemon.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No Pokémon yet. Hatch an egg!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pokemon) { p ->
                    Card {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = p.nickname + if (p.isShiny) " ✨" else "",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Dex #${p.dex}")
                            Text("Level ${p.level}")
                        }
                    }
                }
            }
        }
    }
}
