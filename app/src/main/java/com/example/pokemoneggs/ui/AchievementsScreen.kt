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

data class Achievement(val title: String, val description: String, val unlocked: Boolean)

@Composable
fun AchievementsScreen(vm: GameViewModel, onBack: () -> Unit) {
    val profile by vm.profile.collectAsState()
    val pokemon by vm.pokemon.collectAsState()

    val achievements = listOf(
        Achievement("First Hatch", "Hatch your first egg", (profile?.eggsHatched ?: 0) >= 1),
        Achievement("Collector", "Own 6 Pokémon", pokemon.size >= 6),
        Achievement("Veteran", "Own 12 Pokémon", pokemon.size >= 12)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(achievements) { a ->
                Card {
                    Column(Modifier.padding(16.dp)) {
                        Text(a.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(a.description)
                        Spacer(Modifier.height(8.dp))
                        AssistChip(
                            onClick = {},
                            label = { Text(if (a.unlocked) "Unlocked" else "Locked") }
                        )
                    }
                }
            }
        }
    }
}
