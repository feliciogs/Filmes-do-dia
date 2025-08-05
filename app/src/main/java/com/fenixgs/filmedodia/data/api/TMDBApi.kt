package com.fenixgs.filmedodia.data.api

import com.fenixgs.filmedodia.data.api.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovieByName(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse
}
