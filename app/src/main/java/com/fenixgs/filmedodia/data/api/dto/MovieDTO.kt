package com.fenixgs.filmedodia.data.api.dto

data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double,
    val release_date: String
)
