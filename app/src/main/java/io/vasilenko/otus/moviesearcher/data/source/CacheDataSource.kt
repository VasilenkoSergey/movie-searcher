package io.vasilenko.otus.moviesearcher.data.source

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface CacheDataSource : TopMoviesDataSource {

    fun setMovies(movieList: List<MovieEntity>)

    fun addMovies(movieList: List<MovieEntity>)

    fun isCacheEmpty(): Boolean

    fun clearCache()
}