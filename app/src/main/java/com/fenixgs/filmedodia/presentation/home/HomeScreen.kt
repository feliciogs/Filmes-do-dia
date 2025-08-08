package com.fenixgs.filmedodia.presentation.home

import MovieDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.fenixgs.filmedodia.domain.model.Genre
import com.fenixgs.filmedodia.presentation.components.MovieCard
import org.koin.androidx.compose.koinViewModel

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val genreMoviesMap by viewModel.genreMoviesMap.collectAsState()
    val genres by viewModel.preferredGenres.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedMovie by remember { mutableStateOf<MovieDTO?>(null) }
    var selectedGenre by remember { mutableStateOf<Genre?>(null) }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refreshAllGenres() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Filmes do Dia",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Selecionamos os melhores para você",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.refreshAllGenres() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Atualizar sugestões")
                    }
                }
            }

            genres.forEach { genre ->
                val movies = genreMoviesMap[genre].orEmpty()

                if (movies.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = genre.name,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(movies) { movie ->
                                    MovieCard(
                                        movie = movie,
                                        modifier = Modifier
                                            .width(180.dp)
                                            .padding(vertical = 8.dp),
                                        onClick = {
                                            selectedMovie = movie
                                            selectedGenre = genre
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (isLoading && genreMoviesMap.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }

    selectedMovie?.let { movie ->
        selectedGenre?.let { genre ->
            MovieDialog(
                movie = movie,
                onDismiss = { selectedMovie = null },
                onMarkAsWatched = {
                    viewModel.markMovieAsWatched(genre, movie)
                    selectedMovie = null
                }
            )
        }
    }
}




