package io.vasilenko.otus.moviesearcher.data.repo

import io.reactivex.Observable
import io.vasilenko.otus.moviesearcher.core.applySchedulers
import io.vasilenko.otus.moviesearcher.data.source.TopMoviesDataSource
import io.vasilenko.otus.moviesearcher.data.source.local.LocalTopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.repo.TopMoviesRepo

class TopMoviesRepoImpl(
    private val topMoviesLocalDataSource: LocalTopMoviesDataSource,
    private val topMoviesRemoteDataSource: TopMoviesDataSource
) : TopMoviesRepo {

    override fun getAllMovies(): Observable<List<MovieEntity>> {
        return if (topMoviesLocalDataSource.isCacheEmpty()) {
            topMoviesRemoteDataSource.getAllMovies().doOnNext {
                topMoviesLocalDataSource.setMovies(it)
            }.applySchedulers()
        } else {
            topMoviesLocalDataSource.getAllMovies()
        }
    }

    override fun getAllMoviesByPage(page: Int): Observable<List<MovieEntity>> {
        return topMoviesRemoteDataSource.getAllMovies(page).doOnNext {
            topMoviesLocalDataSource.addMovies(it)
        }.applySchedulers()
    }

    override fun refreshAllMovies(): Observable<List<MovieEntity>> {
        topMoviesLocalDataSource.clearCache()
        return topMoviesRemoteDataSource.getAllMovies().doOnNext {
            topMoviesLocalDataSource.setMovies(it)
        }.applySchedulers()
    }
}