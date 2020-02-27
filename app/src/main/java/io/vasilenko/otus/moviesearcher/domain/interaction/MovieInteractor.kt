package io.vasilenko.otus.moviesearcher.domain.interaction

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface MovieInteractor {

    fun searchTopMovies(listener: TopMoviesSearchListener): List<MovieEntity>

    fun searchNextTopMovies(listener: NextTopMoviesSearchListener, page: Int): List<MovieEntity>

    fun reloadTopMovies(listener: TopMoviesSearchListener)

    fun searchFavoriteMovies(): List<MovieEntity>

    fun isMovieFavorite(movieEntity: MovieEntity): Boolean

    fun addMovieToFavorites(movieEntity: MovieEntity)

    fun removeMovieFromFavorites(movieEntity: MovieEntity)

    interface TopMoviesSearchListener {
        fun onSearchFinished(movies: List<MovieEntity>)
        fun onSearchFailure(t: Throwable?)
    }

    interface NextTopMoviesSearchListener {
        fun onNextSearchFinished(movies: List<MovieEntity>)
        fun onNextSearchFailure(t: Throwable?)
    }
}