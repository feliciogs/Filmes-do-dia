package com.fenixgs.filmedodia.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.fenixgs.filmedodia.data.repository.MovieRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _displayName = MutableStateFlow("Usuário")
    val displayName: StateFlow<String> = _displayName

    private val _watchedMoviesDetailed = MutableStateFlow<List<MovieDTO>>(emptyList())
    val watchedMoviesDetailed: StateFlow<List<MovieDTO>> = _watchedMoviesDetailed

    init {
        val user = FirebaseAuth.getInstance().currentUser
        _displayName.value = user?.displayName ?: "Usuário"
        loadWatchedMovies()
    }

    private fun loadWatchedMovies() {
        viewModelScope.launch {
            val movies = repository.getWatchedMoviesDetailed()
            _watchedMoviesDetailed.value = movies
        }
    }

    fun refreshWatchedMovies() {
        viewModelScope.launch {
            val movies = repository.getWatchedMoviesDetailed()
            _watchedMoviesDetailed.value = movies
        }
    }

}

