package com.example.pokemoneggs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    // Profile
    @Query("SELECT * FROM profile WHERE `key` = 1")
    fun observeProfile(): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(p: ProfileEntity)

    // Eggs
    @Query("SELECT * FROM eggs ORDER BY createdAtEpochMs ASC")
    fun observeEggs(): Flow<List<EggEntity>>

    @Insert
    suspend fun insertEgg(e: EggEntity): Long

    @Update
    suspend fun updateEgg(e: EggEntity)

    @Query("DELETE FROM eggs WHERE id = :id")
    suspend fun deleteEgg(id: Long)

    @Query("DELETE FROM eggs")
    suspend fun deleteAllEggs()

    // Pokémon
    @Query("SELECT * FROM pokemon ORDER BY createdAtEpochMs DESC")
    fun observePokemon(): Flow<List<PokemonEntity>>

    @Insert
    suspend fun insertPokemon(p: PokemonEntity): Long

    @Update
    suspend fun updatePokemon(p: PokemonEntity)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()

    @Query("DELETE FROM pokemon WHERE id = :id")
    suspend fun deletePokemon(id: Long)
}
