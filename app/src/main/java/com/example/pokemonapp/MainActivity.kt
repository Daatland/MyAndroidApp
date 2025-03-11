package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pokemonapp.ui.CreatePokemonScreen
import com.example.pokemonapp.ui.PokemonScreen
import com.example.pokemonapp.ui.ShowCreatedPokemonScreen
import com.example.pokemonapp.ui.HomeScreen
import com.example.pokemonapp.ui.theme.PokemonAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomeScreen(navController) }
                        composable("create_pokemon") { CreatePokemonScreen() }
                        composable("show_created_pokemon") { ShowCreatedPokemonScreen() }
                        composable("show_pokemon_api") { PokemonScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") },
            icon = {},
            label = { Text("Hjem", color = Color.White) }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "create_pokemon",
            onClick = { navController.navigate("create_pokemon") },
            icon = {},
            label = { Text("Laboratorium", color = Color.White) }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "show_created_pokemon",
            onClick = { navController.navigate("show_created_pokemon") },
            icon = {},
            label = { Text("Pokemons", color = Color.White) }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "show_pokemon_api",
            onClick = { navController.navigate("show_pokemon_api") },
            icon = {},
            label = { Text("Pokedex", color = Color.White) }
        )
    }
}

