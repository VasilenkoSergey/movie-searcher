package io.vasilenko.otus.moviesearcher.data.source.local

import io.vasilenko.otus.moviesearcher.data.mapper.MovieDtoMapper
import io.vasilenko.otus.moviesearcher.data.source.TopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor

class LocalTopMoviesDataSource(private val movieMapper: MovieDtoMapper) :
    TopMoviesDataSource {

    override fun getAllMovies(
        listener: MovieInteractor.TopMoviesSearchListener,
        page: Int
    ): List<MovieEntity> {
        return emptyList()
    }
}