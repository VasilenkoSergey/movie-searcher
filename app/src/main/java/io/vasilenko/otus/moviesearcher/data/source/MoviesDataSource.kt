package io.vasilenko.otus.moviesearcher.data.source

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface MoviesDataSource {

    fun getMovies(): List<MovieEntity>
}