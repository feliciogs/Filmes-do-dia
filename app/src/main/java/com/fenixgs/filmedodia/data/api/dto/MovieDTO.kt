package com.fenixgs.filmedodia.data.api.dto

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Float
)
