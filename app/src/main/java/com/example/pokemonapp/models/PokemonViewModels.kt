package com.example.pokemonapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.models.PokemonType
import com.example.pokemonapp.models.Sprites
import com.example.pokemonapp.models.TypeInfo
import com.example.pokemonapp.network.ApiClient
import com.example.pokemonapp.network.PokeApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PokemonViewModel : ViewModel() {
    private val apiService: PokeApiService = ApiClient.retrofit.create(PokeApiService::class.java)

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Paginering variabler
    private var currentOffset = 0
    private val limit = 100
    private var isLoading = false

    //Dummy fallback-Pokemon hvis API-et feiler
    private val fallbackPokemonList = listOf(
        Pokemon(1, "Pikachu", 4, 60, Sprites(null), listOf(PokemonType(TypeInfo("Electric")))),
        Pokemon(2, "Charmander", 6, 85, Sprites(null), listOf(PokemonType(TypeInfo("Fire")))),
        Pokemon(3, "Squirtle", 5, 90, Sprites(null), listOf(PokemonType(TypeInfo("Water")))),
        Pokemon(4, "Bulbasaur", 7, 69, Sprites(null), listOf(PokemonType(TypeInfo("Grass"))))
    )

    init {
        getPokemonList()
    }

    //Henter Pokemon fra API og faller tilbake på dummy-data om nødvendig
    fun getPokemonList() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val fetchedPokemon = mutableListOf<Pokemon>()
                for (id in (currentOffset + 1)..(currentOffset + limit)) {
                    val response = apiService.getPokemonById(id)
                    if (response.isSuccessful && response.body() != null) {
                        fetchedPokemon.add(response.body()!!)
                    }
                }

                _pokemonList.value = _pokemonList.value + fetchedPokemon
                currentOffset += limit
                isLoading = false
            } catch (e: HttpException) {
                _errorMessage.value = "Serverfeil: ${e.message}"
                _pokemonList.value = fallbackPokemonList
                isLoading = false
            } catch (e: IOException) {
                _errorMessage.value = "Nettverksfeil: Sjekk internettforbindelsen"
                _pokemonList.value = fallbackPokemonList
                isLoading = false
            }
        }
    }
}
