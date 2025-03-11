package com.example.pokemonapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.database.PokemonDatabase
import com.example.pokemonapp.models.CreatedPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CreatePokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()

    val allPokemon: Flow<List<CreatedPokemon>> = pokemonDao.getAllPokemon()

    fun addPokemon(name: String, type: String, height: Int, weight: Int, imageRes: Int?) {
        viewModelScope.launch {
            val newPokemon = CreatedPokemon(name = name, type = type, height = height, weight = weight, imageRes = imageRes)
            pokemonDao.insertPokemon(newPokemon)
        }
    }

    fun deletePokemon(pokemon: CreatedPokemon) {
        viewModelScope.launch {
            pokemonDao.deletePokemon(pokemon)
        }
    }
}
