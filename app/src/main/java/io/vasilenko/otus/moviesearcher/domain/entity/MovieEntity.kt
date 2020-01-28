package io.vasilenko.otus.moviesearcher.domain.entity

data class MovieEntity(
    val id: Long? = null,
    val title: String,
    val description: String,
    val rating: String,
    val imgName: String
)