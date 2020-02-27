package io.vasilenko.otus.moviesearcher.data.source.local

import io.vasilenko.otus.moviesearcher.data.local.LocalTopMoviesStorage
import io.vasilenko.otus.moviesearcher.data.mapper.MovieDtoMapper
import io.vasilenko.otus.moviesearcher.data.source.CacheDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor

class LocalTopMoviesDataSource(private val mapper: MovieDtoMapper) : CacheDataSource {

    override fun getAllMovies(
        listener: MovieInteractor.TopMoviesSearchListener
    ): List<MovieEntity> {
        val movies =
            mapper.mapLocalMovieDtoToMovieEntity(LocalTopMoviesStorage.movies.toList())
        listener.onSearchFinished(movies)
        return emptyList()
    }

    override fun getMoviesByPage(
        listener: MovieInteractor.NextTopMoviesSearchListener,
        page: Int
    ): List<MovieEntity> {
        return emptyList()
    }

    override fun setMovies(movieList: List<MovieEntity>) {
        val movies = movieList.map { mapper.mapMovieEntityToLocalMovie(it) }
        clearCache()
        LocalTopMoviesStorage.movies.addAll(movies)
    }

    override fun addMovies(movieList: List<MovieEntity>) {
        val movies = movieList.map { mapper.mapMovieEntityToLocalMovie(it) }
        LocalTopMoviesStorage.movies.addAll(movies)
    }

    override fun isCacheEmpty(): Boolean {
        return LocalTopMoviesStorage.movies.isEmpty()
    }

    override fun clearCache() {
        LocalTopMoviesStorage.movies.clear()
    }
}