package com.fenixgs.filmedodia.domain.usecase

import com.fenixgs.filmedodia.data.repository.MovieRepository

class GetMovieByTitleUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(title: String, apiKey: String) =
        repository.getMovieByTitle(title, apiKey)
}
