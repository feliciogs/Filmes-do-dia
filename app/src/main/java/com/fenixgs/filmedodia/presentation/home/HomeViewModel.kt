package com.fenixgs.filmedodia.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenixgs.filmedodia.data.api.dto.MovieOMDbResponse
import com.fenixgs.filmedodia.domain.usecase.GetMovieByTitleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getMovieByTitleUseCase: GetMovieByTitleUseCase) : ViewModel() {

    private val _movie = MutableStateFlow<MovieOMDbResponse?>(null)
    val movie: StateFlow<MovieOMDbResponse?> = _movie

    fun loadMovie(title: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val result = getMovieByTitleUseCase(title, apiKey)
                _movie.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
