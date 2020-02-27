package io.vasilenko.otus.moviesearcher.data.repo

import io.vasilenko.otus.moviesearcher.data.source.TopMoviesDataSource
import io.vasilenko.otus.moviesearcher.data.source.local.LocalTopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.domain.repo.TopMoviesRepo

class TopMoviesRepoImpl(
    private val topMoviesLocalDataSource: LocalTopMoviesDataSource,
    private val topMoviesRemoteDataSource: TopMoviesDataSource
) : TopMoviesRepo {

    override fun getAllMovies(
        listener: MovieInteractor.TopMoviesSearchListener
    ): List<MovieEntity> {
        if (topMoviesLocalDataSource.isCacheEmpty()) {
            topMoviesRemoteDataSource.getAllMovies(listener)
        } else {
            topMoviesLocalDataSource.getAllMovies(listener)
        }
        return emptyList()
    }

    override fun getAllMoviesByPage(
        listener: MovieInteractor.NextTopMoviesSearchListener,
        page: Int
    ): List<MovieEntity> {
        topMoviesRemoteDataSource.getMoviesByPage(listener, page)
        return emptyList()
    }

    override fun refreshAllMovies(listener: MovieInteractor.TopMoviesSearchListener) {
        topMoviesLocalDataSource.clearCache()
        topMoviesRemoteDataSource.getAllMovies(listener)
    }
}