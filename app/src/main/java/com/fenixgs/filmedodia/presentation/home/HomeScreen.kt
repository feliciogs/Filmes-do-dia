package com.fenixgs.filmedodia.presentation.home

import com.fenixgs.filmedodia.presentation.components.MovieCard
import MovieDialog
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
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.fenixgs.filmedodia.domain.model.Genre
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val movies by viewModel.movies.collectAsState()
    val genres by viewModel.genres.collectAsState()

    var selectedMovie by remember { mutableStateOf<MovieDTO?>(null) }
    var selectedGenre by remember { mutableStateOf<Genre?>(null) }

    LaunchedEffect(Unit) {
        if (selectedGenre == null && genres.isNotEmpty()) {
            selectedGenre = genres.first()
        }
    }

    LaunchedEffect(selectedGenre) {
        selectedGenre?.let {
            viewModel.loadUnwatchedMoviesByGenre(it.id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = selectedGenre?.name ?: "Filmes",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Selecionamos os melhores filmes para você hoje",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(genres) { genre ->
                val isSelected = genre == selectedGenre
                Text(
                    text = genre.name,
                    modifier = Modifier
                        .clickable { selectedGenre = genre }
                        .padding(vertical = 4.dp),
                    style = if (isSelected)
                        MaterialTheme.typography.titleMedium
                    else
                        MaterialTheme.typography.bodyMedium,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (movies.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie = movie,
                        modifier = Modifier
                            .width(180.dp)
                            .padding(vertical = 8.dp),
                        onClick = { selectedMovie = movie }
                    )
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
