package com.example.pokemonapp.database

import androidx.room.*
import com.example.pokemonapp.models.CreatedPokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: CreatedPokemon)

    @Query("SELECT * FROM created_pokemon")
    fun getAllPokemon(): Flow<List<CreatedPokemon>>

    @Delete
    suspend fun deletePokemon(pokemon: CreatedPokemon)
}

