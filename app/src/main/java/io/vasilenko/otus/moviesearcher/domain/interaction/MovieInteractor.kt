package io.vasilenko.otus.moviesearcher.domain.interaction

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface MovieInteractor {

    fun searchTopMovies(): List<MovieEntity>

    fun searchFavoriteMovies(): List<MovieEntity>

    fun isMovieFavorite(movieEntity: MovieEntity): Boolean

    fun addMovieToFavorites(movieEntity: MovieEntity)

    fun removeMovieFromFavorites(movieEntity: MovieEntity)
}