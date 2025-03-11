package com.example.pokemonapp.models

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    val sprites: Sprites,

    @SerializedName("types")
    val types: List<PokemonType>
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String? = null
)

data class PokemonType(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)
