package com.example.pokemoneggs

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemoneggs.ui.*

@Composable
fun AppRoot() {

    val vm: GameViewModel = viewModel()

    var screen by remember { mutableStateOf("setup") }

    when (screen) {
        "setup" -> SetupScreen(
            onCreateProfile = {
                vm.bootstrap(it)
                screen = "home"
            }
        )

        "home" -> HomeScreen(
            vm = vm,
            onNav = { screen = it }
        )

        "eggs" -> EggsScreen(
            vm = vm,
            onBack = { screen = "home" }
        )

        "team" -> TeamScreen(
            vm = vm,
            onBack = { screen = "home" }
        )

        "pokedex" -> PokedexScreen(
            vm = vm,
            onBack = { screen = "home" }
        )

        "achievements" -> AchievementsScreen(
            vm = vm,
            onBack = { screen = "home" }
        )
    }
}
