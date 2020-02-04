package io.vasilenko.otus.moviesearcher.data.source

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface FavoritesMoviesDataSource {

    fun getAllMovies(): List<MovieEntity>

    fun existMovieByEntity(movie: MovieEntity): Boolean

    fun addMovie(movie: MovieEntity)

    fun delMovie(movie: MovieEntity)
}