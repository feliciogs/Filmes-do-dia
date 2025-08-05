package com.fenixgs.filmedodia.presentation.home

import MovieCard
import MovieDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val movies by viewModel.movies.collectAsState()
    var selectedMovie by remember { mutableStateOf<MovieDTO?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadUnwatchedDramaMovies()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Filmes de Drama para hoje",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        if (movies.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies) { movie ->
                    MovieCard(movie = movie) {
                        selectedMovie = movie
                    }
                }
            }
        }

        selectedMovie?.let { movie ->
            MovieDialog(
                movie = movie,
                onDismiss = { selectedMovie = null },
                onMarkAsWatched = {
                    viewModel.markMovieAsWatched(movie.title)
                    selectedMovie = null
                }
            )
        }
    }
}



