package io.vasilenko.otus.moviesearcher.data.local

data class LocalMovieDto(
    val title: String,
    val description: String,
    val rating: String,
    var posterPath: String? = null,
    var backdropPath: String? = null
)