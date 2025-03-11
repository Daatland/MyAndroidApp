package com.example.pokemonapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "created_pokemon")
data class CreatedPokemon(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val height: Int,
    val weight: Int,
    val imageRes: Int?
)
