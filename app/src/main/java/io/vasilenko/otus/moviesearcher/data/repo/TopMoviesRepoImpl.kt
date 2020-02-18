package io.vasilenko.otus.moviesearcher.data.repo

import io.vasilenko.otus.moviesearcher.data.source.TopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.domain.repo.TopMoviesRepo

class TopMoviesRepoImpl(
    private val topMoviesLocalDataSource: TopMoviesDataSource,
    private val topMoviesRemoteDataSource: TopMoviesDataSource
) : TopMoviesRepo {

    override fun getAllMovies(
        listener: MovieInteractor.TopMoviesSearchListener,
        page: Int
    ): List<MovieEntity> {
        return topMoviesRemoteDataSource.getAllMovies(listener, page)
    }
}