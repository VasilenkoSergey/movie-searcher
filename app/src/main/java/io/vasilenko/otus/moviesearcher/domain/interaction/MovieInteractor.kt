package io.vasilenko.otus.moviesearcher.domain.interaction

import io.reactivex.Observable
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface MovieInteractor {

    fun searchTopMovies(): Observable<List<MovieEntity>>

    fun searchNextTopMovies(page: Int): Observable<List<MovieEntity>>

    fun reloadTopMovies(): Observable<List<MovieEntity>>

    fun searchFavoriteMovies(): List<MovieEntity>

    fun isMovieFavorite(movieEntity: MovieEntity): Boolean

    fun addMovieToFavorites(movieEntity: MovieEntity)

    fun removeMovieFromFavorites(movieEntity: MovieEntity)
}