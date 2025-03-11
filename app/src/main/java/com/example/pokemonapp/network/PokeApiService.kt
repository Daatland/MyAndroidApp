package com.example.pokemonapp.network

import com.example.pokemonapp.models.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): Response<Pokemon>
}
