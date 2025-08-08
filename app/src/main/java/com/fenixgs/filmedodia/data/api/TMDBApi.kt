package com.fenixgs.filmedodia.data.api

import com.fenixgs.filmedodia.data.api.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.random.Random
import kotlin.random.nextInt

interface TMDBApi {

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = Random.nextInt(500)
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovieByName(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse
}
