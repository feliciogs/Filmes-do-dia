package com.fenixgs.filmedodia.data.api

import com.fenixgs.filmedodia.data.api.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int
    ): MovieResponse
}
