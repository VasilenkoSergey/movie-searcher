package io.vasilenko.otus.moviesearcher.data.source

import io.reactivex.Observable
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface TopMoviesDataSource {

    fun getAllMovies(page: Int? = null): Observable<List<MovieEntity>>
}