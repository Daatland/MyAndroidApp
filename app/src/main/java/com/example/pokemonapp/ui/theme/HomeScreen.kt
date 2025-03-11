package com.example.pokemonapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokemonapp.R

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokemonbackground),
            contentDescription = "Bakgrunn",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                for (dx in listOf(-2f, 2f, 0f, 0f)) {
                    for (dy in listOf(0f, 0f, -2f, 2f)) {
                        Text(
                            text = "MyPokémon",
                            fontSize = 56.sp,
                            fontFamily = pokemonFontSolid,
                            color = Color.Black,
                            modifier = Modifier.offset(dx.dp, dy.dp)
                        )
                    }
                }
                Text(
                    text = "MyPokémon",
                    fontSize = 56.sp,
                    fontFamily = pokemonFontSolid,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomButton("Laboratorium") { navController.navigate("create_pokemon") }
                CustomButton("Dine Pokemons") { navController.navigate("show_created_pokemon") }
                CustomButton("Pokedex") { navController.navigate("show_pokemon_api") }
            }
        }
    }
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF7C70B),
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(0.7f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            for (dx in listOf(-1f, 1f, 0f, 0f)) {
                for (dy in listOf(0f, 0f, -1f, 1f)) {
                    Text(
                        text = text,
                        fontSize = 20.sp,
                        fontFamily = pokemonFontSolid,
                        color = Color.Black,
                        modifier = Modifier.offset(dx.dp, dy.dp)
                    )
                }
            }
            Text(
                text = text,
                fontSize = 20.sp,
                fontFamily = pokemonFontSolid,
                color = Color.White
            )
        }
    }
}
