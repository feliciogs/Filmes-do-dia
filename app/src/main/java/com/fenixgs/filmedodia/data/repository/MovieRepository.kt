package com.fenixgs.filmedodia.data.repository

import com.fenixgs.filmedodia.data.api.OMDbApi
import com.fenixgs.filmedodia.data.api.dto.MovieOMDbResponse

class MovieRepository(private val api: OMDbApi) {
    suspend fun getMovieByTitle(title: String, apiKey: String): MovieOMDbResponse {
        return api.getMovieByTitle(title, apiKey)
    }
}
