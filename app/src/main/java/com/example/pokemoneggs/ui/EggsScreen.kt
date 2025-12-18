package com.example.pokemoneggs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoneggs.GameViewModel
import com.example.pokemoneggs.data.db.EggEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EggsScreen(
    vm: GameViewModel,
    onBack: () -> Unit
) {
    val eggs by vm.eggs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eggs") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->

        if (eggs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No eggs yet. Add one from the home screen.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(eggs) { egg ->
                    EggCard(
                        egg = egg,
                        onTap = { vm.tapEgg(egg) },
                        onHatch = { vm.hatchEgg(egg) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EggCard(
    egg: EggEntity,
    onTap: () -> Unit,
    onHatch: () -> Unit
) {
    val progress =
        (egg.currentTaps.toFloat() / egg.requiredTaps.toFloat())
            .coerceIn(0f, 1f)

    val ready = egg.currentTaps >= egg.requiredTaps

    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Egg #${egg.id}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            // ✅ NEW, NON-DEPRECATED API
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(Modifier.height(6.dp))

            Text("${egg.currentTaps} / ${egg.requiredTaps} taps")

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onTap,
                enabled = !ready
            ) {
                Text("Tap Egg")
            }

            if (ready) {
                Spacer(Modifier.height(8.dp))
                Button(onClick = onHatch) {
                    Text("Hatch Egg")
                }
            }
        }
    }
}
