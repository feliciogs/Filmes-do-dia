// data/api/OMDbApi.kt
package com.fenixgs.filmedodia.data.api

import com.fenixgs.filmedodia.data.api.dto.MovieOMDbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApi {
    @GET(".")
    suspend fun getMovieByTitle(
        @Query("t") title: String,
        @Query("apikey") apiKey: String
    ): MovieOMDbResponse
}
