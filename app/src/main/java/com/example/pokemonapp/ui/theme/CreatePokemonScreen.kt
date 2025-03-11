@file:OptIn(ExperimentalMaterial3Api::class) // Godtar eksperimentelle Material3 API-er

package com.example.pokemonapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemonapp.R
import com.example.pokemonapp.viewmodel.CreatePokemonViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreatePokemonScreen(
    viewModel: CreatePokemonViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    var saveMessage by remember { mutableStateOf("") }

    // Definerer bildet basert på input eller setter en placeholder
    val imageRes = when (imageUrl.lowercase()) {
        "sierra" -> R.drawable.sierra
        "skyrim" -> R.drawable.skyrim
        "picalica" -> R.drawable.picalica
        "optimus" -> R.drawable.optimus
        "ghostler" -> R.drawable.ghostler
        else -> R.drawable.placeholder
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.createpokemonbackground),
            contentDescription = "Bakgrunn",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleWithOutline("Pokemon")
            TitleWithOutline("Laboratorium")

            Spacer(modifier = Modifier.height(20.dp))

            // Tekstfelter for å legge inn Pokemon data
            CustomOutlinedTextField(value = name, label = "Navn") { name = it }
            CustomOutlinedTextField(value = type, label = "Type") { type = it }
            CustomOutlinedTextField(value = height, label = "Høyde (dm)") { height = it }
            CustomOutlinedTextField(value = weight, label = "Vekt (hg)") { weight = it }
            CustomOutlinedTextField(value = imageUrl, label = "Bildenavn (sierra, skyrim, picalica, optimus, ghostler)") { imageUrl = it }

            Spacer(modifier = Modifier.height(50.dp))

            // Knapp for å lage Pokémon
            Button(
                onClick = {
                    val h = height.toIntOrNull() ?: 0
                    val w = weight.toIntOrNull() ?: 0

                    if (name.isNotBlank() && type.isNotBlank()) {
                        viewModel.addPokemon(name, type, h, w, imageRes)

                        saveMessage = "Pokémon Laget!"
                        coroutineScope.launch {
                            delay(2000)
                            saveMessage = ""
                        }


                        name = ""
                        type = ""
                        height = ""
                        weight = ""
                        imageUrl = ""
                    } else {
                        saveMessage = "Fyll ut navn og type!"
                        coroutineScope.launch {
                            delay(2000)
                            saveMessage = ""
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.Yellow),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(0.75f)
            ) {
                Text(text = "Lag Pokémon", fontSize = 18.sp, fontFamily = pokemonFontSolid)
            }

            Spacer(modifier = Modifier.height(24.dp))


            if (saveMessage.isNotEmpty()) {
                SaveMessageBox(message = saveMessage)
            }
        }
    }
}

// Her lager jeg en tittel med kontur
@Composable
fun TitleWithOutline(text: String) {
    Box(contentAlignment = Alignment.Center) {
        for (dx in listOf(-2f, 2f, 0f, 0f)) {
            for (dy in listOf(0f, 0f, -2f, 2f)) {
                Text(
                    text = text,
                    fontSize = 36.sp,
                    fontFamily = pokemonFontSolid,
                    color = Color.Black,
                    modifier = Modifier.offset(dx.dp, dy.dp)
                )
            }
        }
        Text(text = text, fontSize = 36.sp, fontFamily = pokemonFontSolid, color = Color.White)
    }
}

// Kode for design av tekstboksene
@Composable
fun CustomOutlinedTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(0.85f),
        colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
    )
    Spacer(modifier = Modifier.height(10.dp))
}


@Composable
fun SaveMessageBox(message: String) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .background(Color.Black, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, fontSize = 22.sp, color = Color.Yellow, fontWeight = FontWeight.Bold)
    }
}
