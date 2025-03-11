package com.example.pokemonapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemonapp.viewmodel.PokemonViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.R
import com.skydoves.landscapist.glide.GlideImage

// Skjerm for å vise Pokémon fra API-et
@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: PokemonViewModel = viewModel()
    val pokemonList by viewModel.pokemonList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    val listState = rememberLazyListState()

    // Sjekker om brukeren er nær bunnen av listen
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= layoutInfo.totalItemsCount - 5 // Laster mer når 5 elementer gjenstår
        }
    }

    // Kaller API når brukeren scroller til bunnen
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.getPokemonList()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7C70B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Overskrift med outline-effekt
        Box(contentAlignment = Alignment.Center) {
            for (dx in listOf(-2f, 2f, 0f, 0f)) {
                for (dy in listOf(0f, 0f, -2f, 2f)) {
                    Text(
                        text = "Pokedex",
                        fontSize = 36.sp,
                        fontFamily = pokemonFontSolid,
                        color = Color.Black,
                        modifier = Modifier.offset(dx.dp, dy.dp)
                    )
                }
            }
            Text(
                text = "Pokedex",
                fontSize = 36.sp,
                fontFamily = pokemonFontSolid,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Søkefelt for Pokemon
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Søk Pokémon (Navn / ID)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        val filteredList = if (searchText.text.isEmpty()) {
            pokemonList
        } else {
            pokemonList.filter {
                it.name.contains(searchText.text, ignoreCase = true) ||
                        it.id.toString().contains(searchText.text)
            }
        }

        // Viser Pokemon-liste
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { pokemon ->
                PokemonItem(pokemon)
            }

            item {
                if (shouldLoadMore) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp) // Endre til ønsket størrelse, f.eks. 30.dp
                            .padding(8.dp),
                        strokeWidth = 2.dp // Gjør den tynnere om ønskelig
                    )
                }
            }
        }
    }
}

// Viser en enkelt Pokemon i listen
@Composable
fun PokemonItem(pokemon: com.example.pokemonapp.models.Pokemon) {
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
                // Laster inn bilde fra API eller viser placeholder
                GlideImage(
                    imageModel = { pokemon.sprites.frontDefault ?: R.drawable.placeholder },
                    modifier = Modifier.size(80.dp),
                    loading = {
                        CircularProgressIndicator()
                    },
                    failure = {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "Placeholder image",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Viser Pokemon-informasjon
                Column {
                    Text(text = pokemon.name.capitalize(), style = MaterialTheme.typography.headlineSmall, color = Color.Black)
                    Text(text = "ID: ${pokemon.id}", color = Color.Black)
                    Text(text = "Høyde: ${pokemon.height} dm", color = Color.Black)
                    Text(text = "Vekt: ${pokemon.weight} hg", color = Color.Black)
                    Text(text = "Type: ${pokemon.types.joinToString { it.type.name.capitalize() }}", color = Color.Black)
                }
            }
        }
    }
}
