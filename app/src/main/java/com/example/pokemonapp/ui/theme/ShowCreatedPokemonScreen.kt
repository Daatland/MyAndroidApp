package com.example.pokemonapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemonapp.models.CreatedPokemon
import com.example.pokemonapp.viewmodel.CreatePokemonViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.sp

// Viser Pokemon laget av brukeren
@Composable
fun CreatedPokemonItem(pokemon: CreatedPokemon, onDelete: (CreatedPokemon) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (pokemon.imageRes != null) {
                    Image(
                        painter = painterResource(id = pokemon.imageRes),
                        contentDescription = "Pokemon Image",
                        modifier = Modifier.size(80.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                // Viser Pokemoninformasjon
                Column {
                    Text(text = "Navn: ${pokemon.name}", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
                    Text(text = "Type: ${pokemon.type}", color = Color.Black)
                    Text(text = "Høyde: ${pokemon.height} dm", color = Color.Black)
                    Text(text = "Vekt: ${pokemon.weight} hg", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Button(
                onClick = { onDelete(pokemon) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Slett", fontSize = 18.sp)
            }
        }
    }
}

// Skjerm for å vise brukerens lagrede Pokémons
@Composable
fun ShowCreatedPokemonScreen(viewModel: CreatePokemonViewModel = viewModel(), modifier: Modifier = Modifier) {
    val pokemonList by viewModel.allPokemon.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7C70B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Overskrift med hvit tekst og svart outline
        Box(contentAlignment = Alignment.Center) {
            for (dx in listOf(-2f, 2f, 0f, 0f)) {
                for (dy in listOf(0f, 0f, -2f, 2f)) {
                    Text(
                        text = "Dine Pokémons",
                        fontSize = 36.sp,
                        fontFamily = pokemonFontSolid,
                        color = Color.Black,
                        modifier = Modifier.offset(dx.dp, dy.dp)
                    )
                }
            }
            Text(
                text = "Dine Pokémons",
                fontSize = 36.sp,
                fontFamily = pokemonFontSolid,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (pokemonList.isEmpty()) {
            Text(
                "Du har ikke laget noen Pokémons ennå. Dra til labratoriumet for å lage dine egne pokémons.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        } else {
            // Viser en liste over brukerens lagrede Pokemon
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemonList) { pokemon ->
                    CreatedPokemonItem(pokemon, onDelete = { viewModel.deletePokemon(it) })
                }
            }
        }
    }
}
