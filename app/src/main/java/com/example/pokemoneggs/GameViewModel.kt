package com.example.pokemoneggs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemoneggs.data.db.AppDatabase
import com.example.pokemoneggs.data.db.EggEntity
import com.example.pokemoneggs.data.db.PokemonEntity
import com.example.pokemoneggs.data.db.ProfileEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).gameDao()

    val profile = dao.observeProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val eggs = dao.observeEggs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val pokemon = dao.observePokemon()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun bootstrap(playerName: String = "Trainer") {
        viewModelScope.launch {
            if (profile.value == null) {
                dao.upsertProfile(
                    ProfileEntity(
                        playerName = playerName,
                        totalTaps = 0,
                        eggsHatched = 0,
                        lastLoginDay = LocalDate.now().toString()
                    )
                )
            }
        }
    }

    fun addEgg() {
        viewModelScope.launch {
            dao.insertEgg(
                EggEntity(
                    requiredTaps = 10,
                    currentTaps = 0,
                    createdAtEpochMs = System.currentTimeMillis()
                )
            )
        }
    }

    fun tapEgg(egg: EggEntity) {
        viewModelScope.launch {
            dao.updateEgg(egg.copy(currentTaps = egg.currentTaps + 1))
        }
    }

    fun hatchEgg(egg: EggEntity) {
        viewModelScope.launch {
            dao.deleteEgg(egg.id)
            dao.insertPokemon(
                PokemonEntity(
                    dex = (1..151).random(),
                    nickname = "New Pokémon",
                    level = 1,
                    isShiny = (0..100).random() == 1,
                    createdAtEpochMs = System.currentTimeMillis()
                )
            )
        }
    }
}
