package io.vasilenko.otus.moviesearcher.domain.repo

import io.reactivex.Observable
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface TopMoviesRepo {

    fun getAllMovies(): Observable<List<MovieEntity>>

    fun getAllMoviesByPage(page: Int): Observable<List<MovieEntity>>

    fun refreshAllMovies(): Observable<List<MovieEntity>>
}