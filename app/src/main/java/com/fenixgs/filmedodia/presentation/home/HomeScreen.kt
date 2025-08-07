package com.fenixgs.filmedodia.presentation.home

import MovieCard
import MovieDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                    viewModel.markMovieAsWatched(movie)
                    selectedMovie = null
                }
            )
        }
    }
}

