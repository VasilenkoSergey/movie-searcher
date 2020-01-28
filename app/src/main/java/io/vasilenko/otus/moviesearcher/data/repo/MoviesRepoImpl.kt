package io.vasilenko.otus.moviesearcher.data.repo

import io.vasilenko.otus.moviesearcher.data.source.MoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.repo.MoviesRepo

class MoviesRepoImpl(
    private val moviesLocalDataSource: MoviesDataSource,
    private val moviesRemoteDataSource: MoviesDataSource
) : MoviesRepo {

    override fun getAllMovies(): List<MovieEntity> {
        return moviesLocalDataSource.getMovies()
    }
}