package io.vasilenko.otus.moviesearcher.domain.repo

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface TopMoviesRepo {

    fun getAllMovies(): List<MovieEntity>
}