package com.example.pokemoneggs.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dex: Int,
    val nickname: String,
    val level: Int,
    val isShiny: Boolean,
    val createdAtEpochMs: Long
)

@Entity(tableName = "eggs")
data class EggEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val requiredTaps: Int,
    val currentTaps: Int,
    val createdAtEpochMs: Long
)

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val key: Int = 1,
    val playerName: String,
    val totalTaps: Long,
    val eggsHatched: Long,
    val lastLoginDay: String
)
