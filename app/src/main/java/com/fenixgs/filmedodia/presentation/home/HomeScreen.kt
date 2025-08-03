package com.fenixgs.filmedodia.presentation.home

import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val movieState by viewModel.movie.collectAsState()
    var title by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Filme") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val apiKey = "179904c7"
            viewModel.loadMovie(title, apiKey)
        }) {
            Text("Buscar Filme")
        }

        Spacer(modifier = Modifier.height(16.dp))

        movieState?.let { movie ->
            Text("Título: ${movie.title}")
            Text("Nota IMDb: ${movie.imdbRating}")
            Text("Gênero: ${movie.genre}")
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                modifier = Modifier.height(300.dp)
            )
        }
    }
}
