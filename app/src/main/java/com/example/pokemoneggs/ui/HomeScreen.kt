package com.example.pokemoneggs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoneggs.GameViewModel

@Composable
fun HomeScreen(vm: GameViewModel, onNav: (String) -> Unit) {
    val profile by vm.profile.collectAsState()
    val eggs by vm.eggs.collectAsState()
    val pokemon by vm.pokemon.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Trainer: ${profile?.playerName ?: "—"}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        Row {
            StatCard("Eggs", eggs.size.toString(), Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            StatCard("Pokemon", pokemon.size.toString(), Modifier.weight(1f))
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { vm.addEgg() }, modifier = Modifier.fillMaxWidth()) {
            Text("Train (Add Egg Progress)")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { onNav("eggs") }) { Text("Eggs") }
        Button(onClick = { onNav("team") }) { Text("Team") }
        Button(onClick = { onNav("pokedex") }) { Text("Pokedex") }
        Button(onClick = { onNav("achievements") }) { Text("Achievements") }
    }
}
