package com.fenixgs.filmedodia.util.mappers

import com.fenixgs.filmedodia.data.api.dto.MovieDto
import com.fenixgs.filmedodia.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        posterUrl = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" },
        rating = vote_average
    )
}
