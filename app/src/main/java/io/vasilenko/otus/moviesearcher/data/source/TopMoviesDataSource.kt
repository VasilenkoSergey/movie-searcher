package io.vasilenko.otus.moviesearcher.data.source

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface TopMoviesDataSource {

    fun getAllMovies(): List<MovieEntity>
}