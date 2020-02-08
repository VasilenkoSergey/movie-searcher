package io.vasilenko.otus.moviesearcher.domain.repo

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface FavoriteMoviesRepo {

    fun getAllMovies(): List<MovieEntity>

    fun existMovieByEntity(movie: MovieEntity): Boolean

    fun addMovie(movie: MovieEntity)

    fun delMovie(movie: MovieEntity)
}