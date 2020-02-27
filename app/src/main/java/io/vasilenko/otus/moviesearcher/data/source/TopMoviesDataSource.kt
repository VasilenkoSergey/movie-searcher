package io.vasilenko.otus.moviesearcher.data.source

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor

interface TopMoviesDataSource {

    fun getAllMovies(listener: MovieInteractor.TopMoviesSearchListener): List<MovieEntity>

    fun getMoviesByPage(
        listener: MovieInteractor.NextTopMoviesSearchListener,
        page: Int
    ): List<MovieEntity>
}