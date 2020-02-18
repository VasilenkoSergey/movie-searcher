package io.vasilenko.otus.moviesearcher.domain.interaction

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface MovieInteractor {

    fun searchTopMovies(listener: TopMoviesSearchListener, page: Int): List<MovieEntity>

    fun searchFavoriteMovies(): List<MovieEntity>

    fun isMovieFavorite(movieEntity: MovieEntity): Boolean

    fun addMovieToFavorites(movieEntity: MovieEntity)

    fun removeMovieFromFavorites(movieEntity: MovieEntity)

    interface TopMoviesSearchListener {
        fun onSearchFinished(movies: List<MovieEntity>)
        fun onSearchFailure(t: Throwable?)
    }
}