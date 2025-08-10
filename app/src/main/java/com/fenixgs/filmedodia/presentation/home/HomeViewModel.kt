package com.fenixgs.filmedodia.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.fenixgs.filmedodia.data.repository.MovieRepository
import com.fenixgs.filmedodia.data.repository.UserPreferencesRepository
import com.fenixgs.filmedodia.domain.model.Genre
import com.fenixgs.filmedodia.domain.model.provider.GenresProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _genreMoviesMap = MutableStateFlow<Map<Genre, List<MovieDTO>>>(emptyMap())
    val genreMoviesMap: StateFlow<Map<Genre, List<MovieDTO>>> = _genreMoviesMap

    private val _multiGenreMovies = MutableStateFlow<List<MovieDTO>>(emptyList())
    val multiGenreMovies: StateFlow<List<MovieDTO>> = _multiGenreMovies

    private val _preferredGenres = MutableStateFlow<List<Genre>>(emptyList())
    val preferredGenres: StateFlow<List<Genre>> = _preferredGenres

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiKey = "f99c0b2933f2922adb57394f5a38f05e"

    init {
        loadPreferredGenres()
    }

    fun loadPreferredGenres() {
        viewModelScope.launch {
            val ids = userPreferencesRepository.getPreferredGenres()
            val allGenres = GenresProvider.genres
            val selectedGenres = allGenres.filter { it.id in ids }
            _preferredGenres.value = selectedGenres
            selectedGenres.forEach { genre ->
                loadUnwatchedMoviesByGenre(genre)
            }
            refreshMultiGenreMovies(selectedGenres)
        }
    }

    private fun loadUnwatchedMoviesByGenre(genre: Genre) {
        viewModelScope.launch {
            val allMovies = repository.getMoviesByGenre(apiKey, genre.id)
            val watchedTitles = repository.getWatchedMovies()
            val unwatched = allMovies.filterNot { it.title in watchedTitles }.take(5)

            _genreMoviesMap.value = _genreMoviesMap.value.toMutableMap().apply {
                put(genre, unwatched)
            }
        }
    }

    fun markMovieAsWatched(genre: Genre, movie: MovieDTO) {
        viewModelScope.launch {
            repository.saveWatchedMovie(movie.title, movie.poster_path ?: "")
            loadUnwatchedMoviesByGenre(genre)
            refreshMultiGenreMovies(preferredGenres.value)
        }
    }

    fun refreshAllGenres() {
        viewModelScope.launch {
            _isLoading.value = true
            val watchedTitles = repository.getWatchedMovies()
            val genres = preferredGenres.value
            val map = mutableMapOf<Genre, List<MovieDTO>>()

            for (genre in genres) {
                val all = repository.getMoviesByGenre(apiKey, genre.id)
                val unwatched = all.filterNot { it.title in watchedTitles }
                map[genre] = unwatched.take(5)
            }

            _genreMoviesMap.value = map

            refreshMultiGenreMovies(genres)

            _isLoading.value = false
        }
    }

    private fun refreshMultiGenreMovies(genres: List<Genre>) {
        if (genres.size < 2) {
            _multiGenreMovies.value = emptyList()
            return
        }

        viewModelScope.launch {
            val watchedTitles = repository.getWatchedMovies()
            val genreIds = genres.map { it.id }

            val movies = repository.getMoviesByGenresAnd(apiKey, genreIds)
            val unwatched = movies.filterNot { it.title in watchedTitles }.take(10) // pode ajustar quantidade

            _multiGenreMovies.value = unwatched
        }
    }
}


