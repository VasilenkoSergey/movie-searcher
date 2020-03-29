package io.vasilenko.otus.moviesearcher.data.source.local

import io.reactivex.Observable
import io.vasilenko.otus.moviesearcher.data.local.LocalTopMoviesStorage
import io.vasilenko.otus.moviesearcher.data.mapper.MovieDtoMapper
import io.vasilenko.otus.moviesearcher.data.source.CacheDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

class LocalTopMoviesDataSource(private val mapper: MovieDtoMapper) : CacheDataSource {

    override fun getAllMovies(page: Int?): Observable<List<MovieEntity>> {
        return Observable.just(mapper.mapLocalMovieDtoToMovieEntity(LocalTopMoviesStorage.movies.toList()))
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