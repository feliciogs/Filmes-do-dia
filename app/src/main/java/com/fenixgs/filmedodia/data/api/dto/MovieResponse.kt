package com.fenixgs.filmedodia.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieDto>
)
