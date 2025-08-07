package com.fenixgs.filmedodia.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.fenixgs.filmedodia.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieDTO>>(emptyList())
    val movies: StateFlow<List<MovieDTO>> = _movies

    private val apiKey = "f99c0b2933f2922adb57394f5a38f05e"

    fun loadUnwatchedDramaMovies() {
        viewModelScope.launch {
            val allMovies = repository.getDramaMovies(apiKey)
            val watchedTitles = repository.getWatchedMovies()
            val unwatched = allMovies.filterNot { it.title in watchedTitles }
            _movies.value = unwatched.take(5)
        }
    }

    fun markMovieAsWatched(title: MovieDTO) {
        viewModelScope.launch {
            repository.saveWatchedMovie(title.title,title.poster_path?: "")
            loadUnwatchedDramaMovies()
        }
    }
}
