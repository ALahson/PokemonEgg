package com.example.pokemoneggs.data

import com.example.pokemoneggs.data.db.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.time.LocalDate

data class Profile(
    val playerName: String = "",
    val totalTaps: Long = 0,
    val eggsHatched: Long = 0,
    val lastLoginDay: String = ""
)

class GameRepository(private val dao: GameDao) {

    fun observeProfile(): Flow<ProfileEntity?> = dao.observeProfile()
    fun observeEggs(): Flow<List<EggEntity>> = dao.observeEggs()
    fun observePokemon(): Flow<List<PokemonEntity>> = dao.observePokemon()

    suspend fun bootstrapIfNeeded() {
        val p = dao.observeProfile().first()
        if (p == null) {
            dao.upsertProfile(
                ProfileEntity(
                    key = 1,
                    playerName = "",
                    totalTaps = 0,
                    eggsHatched = 0,
                    lastLoginDay = LocalDate.now().toString()
                )
            )
        }
    }

    suspend fun createProfile(name: String) {
        val now = LocalDate.now().toString()
        dao.upsertProfile(ProfileEntity(1, name.trim(), 0, 0, now))
        dao.insertEgg(EggEntity(requiredTaps = 10, currentTaps = 0, createdAtEpochMs = System.currentTimeMillis()))
    }

    suspend fun dailyLoginBonusIfNeeded() {
        val p = dao.observeProfile().first() ?: return
        val today = LocalDate.now().toString()
        if (p.lastLoginDay != today) {
            dao.insertEgg(EggEntity(requiredTaps = 12, currentTaps = 0, createdAtEpochMs = System.currentTimeMillis()))
            dao.upsertProfile(p.copy(lastLoginDay = today))
        }
    }

    suspend fun trainTap() {
        val p = dao.observeProfile().first() ?: return
        val newTotal = p.totalTaps + 1
        dao.upsertProfile(p.copy(totalTaps = newTotal))

        val eggs = dao.observeEggs().first()
        for (e in eggs) {
            val updated = e.copy(currentTaps = (e.currentTaps + 1).coerceAtMost(e.requiredTaps))
            dao.updateEgg(updated)
        }

        if (newTotal % 5L == 0L) {
            val team = dao.observePokemon().first()
            for (poke in team) {
                val nextLevel = (poke.level + 1).coerceAtMost(100)
                val evolved = maybeEvolve(poke.copy(level = nextLevel))
                dao.updatePokemon(evolved)
            }
        }

        if (newTotal % 20L == 0L) {
            dao.insertEgg(EggEntity(requiredTaps = 15, currentTaps = 0, createdAtEpochMs = System.currentTimeMillis()))
        }
    }

    suspend fun hatchEgg(eggId: Long, nickname: String) {
        val eggs = dao.observeEggs().first()
        val egg = eggs.firstOrNull { it.id == eggId } ?: return
        if (egg.currentTaps < egg.requiredTaps) return

        val p = dao.observeProfile().first() ?: return
        val seed = (p.totalTaps shl 1) + eggId
        val dex = PokemonCatalog.randomBasicDex(seed)
        val isShiny = (seed % 97L) == 0L

        val poke = PokemonEntity(
            dex = dex,
            nickname = nickname.trim().ifBlank { PokemonCatalog.byDex(dex).name },
            level = 5,
            isShiny = isShiny,
            createdAtEpochMs = System.currentTimeMillis()
        )
        dao.insertPokemon(poke)
        dao.deleteEgg(eggId)
        dao.upsertProfile(p.copy(eggsHatched = p.eggsHatched + 1))
    }

    suspend fun renamePokemon(id: Long, nickname: String) {
        val team = dao.observePokemon().first()
        val target = team.firstOrNull { it.id == id } ?: return
        dao.updatePokemon(target.copy(nickname = nickname.trim().ifBlank { target.nickname }))
    }

    suspend fun resetAll() {
        val prof = dao.observeProfile().first() ?: return
        dao.deleteAllEggs()
        dao.deleteAllPokemon()
        dao.upsertProfile(prof.copy(totalTaps = 0, eggsHatched = 0))
        dao.insertEgg(EggEntity(requiredTaps = 10, currentTaps = 0, createdAtEpochMs = System.currentTimeMillis()))
    }

    private fun maybeEvolve(p: PokemonEntity): PokemonEntity {
        val sp = PokemonCatalog.byDex(p.dex)
        val evolvesAt = sp.evolvesAtLevel
        val evolvesTo = sp.evolvesToDex
        return if (evolvesAt != null && evolvesTo != null && p.level >= evolvesAt) {
            p.copy(dex = evolvesTo)
        } else p
    }
}
