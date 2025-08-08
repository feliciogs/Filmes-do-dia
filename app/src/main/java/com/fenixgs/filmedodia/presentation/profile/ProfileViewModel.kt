package com.fenixgs.filmedodia.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.fenixgs.filmedodia.data.repository.MovieRepository
import com.fenixgs.filmedodia.data.repository.UserPreferencesRepository
import com.fenixgs.filmedodia.domain.model.Genre
import com.fenixgs.filmedodia.domain.model.provider.GenresProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: MovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {

    private val _displayName = MutableStateFlow("Usuário")
    val displayName: StateFlow<String> = _displayName

    private val _watchedMoviesDetailed = MutableStateFlow<List<MovieDTO>>(emptyList())
    val watchedMoviesDetailed: StateFlow<List<MovieDTO>> = _watchedMoviesDetailed

    private val _selectedGenreIds = MutableStateFlow<Set<Int>>(emptySet())
    val selectedGenreIds: StateFlow<Set<Int>> = _selectedGenreIds

    private val _allGenres = MutableStateFlow<List<Genre>>(emptyList())
    val allGenres: StateFlow<List<Genre>> = _allGenres

    init {
        val user = FirebaseAuth.getInstance().currentUser
        _displayName.value = user?.displayName ?: "Usuário"
        loadWatchedMovies()
        loadGenres()
        refreshWatchedMovies()
    }

    private fun loadGenres() {
        _allGenres.value = GenresProvider.genres
    }

    private fun loadWatchedMovies() {
        viewModelScope.launch {
            val movies = repository.getWatchedMoviesDetailed()
            _watchedMoviesDetailed.value = movies
        }
    }

    private fun refreshWatchedMovies() {
        viewModelScope.launch {
            val movies = repository.getWatchedMoviesDetailed()
            _watchedMoviesDetailed.value = movies
        }
    }

    fun loadUserPreferredGenres() {
        viewModelScope.launch {
            val prefs = userPreferencesRepository.getPreferredGenres()
            _selectedGenreIds.value = prefs.toSet()
        }
    }

    fun updatePreferredGenres(genreIds: List<Int>, onComplete: () -> Unit) {
        viewModelScope.launch {
            userPreferencesRepository.savePreferredGenres(genreIds)
            _selectedGenreIds.value = genreIds.toSet()
            onComplete()
        }
    }

}

