# PokemonApp (Android · Jetpack Compose)

A native Android application built with **Jetpack Compose**.
The app allows users to browse Pokémon fetched from the public **PokéAPI**, search by name or ID,
and create custom Pokémon stored locally on the device.

## Features

- 🔎 Search Pokémon by name or ID
- ♾️ Infinite scrolling / lazy loading
- 🌐 Fetches data from the public PokéAPI using Retrofit
- 💾 Create and store custom Pokémon locally using Room
- 🧭 Navigation with Navigation Compose and bottom navigation
- 🎨 Modern UI built with Jetpack Compose (Material 3)

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Retrofit 2**
- **Gson**
- **Kotlin Coroutines**
- **Room (local database)**
- **Hilt (dependency injection)**

## API

This project uses the public PokéAPI:
- Base URL: `https://pokeapi.co/api/v2/`
- Pokémon endpoint: `pokemon/{id}`

## Requirements

- Android Studio (recent version)
- JDK 17 (recommended)
- `minSdk 24`
- `targetSdk 34`
- `compileSdk 34`

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/Daatland/MyAndroidApp.git
