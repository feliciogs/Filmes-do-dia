package com.fenixgs.filmedodia.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieOMDbResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("imdbRating") val imdbRating: String
)
