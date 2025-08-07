package com.fenixgs.filmedodia.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchedMoviesScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val watchedMovies by viewModel.watchedMoviesDetailed.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filmes assistidos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(watchedMovies) { movie ->
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(
                            "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                        ),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .height(100.dp)
                            .width(70.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        }
    }
}
