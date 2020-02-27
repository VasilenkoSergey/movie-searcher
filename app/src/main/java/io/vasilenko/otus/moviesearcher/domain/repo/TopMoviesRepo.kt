package io.vasilenko.otus.moviesearcher.domain.repo

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor

interface TopMoviesRepo {

    fun getAllMovies(listener: MovieInteractor.TopMoviesSearchListener): List<MovieEntity>

    fun getAllMoviesByPage(
        listener: MovieInteractor.NextTopMoviesSearchListener,
        page: Int
    ): List<MovieEntity>

    fun refreshAllMovies(listener: MovieInteractor.TopMoviesSearchListener)
}