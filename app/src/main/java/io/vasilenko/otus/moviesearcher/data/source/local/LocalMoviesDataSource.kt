package io.vasilenko.otus.moviesearcher.data.source.local

import io.vasilenko.otus.moviesearcher.data.local.LocalMovieStorage
import io.vasilenko.otus.moviesearcher.data.mapper.LocalToDomainMovieMapper
import io.vasilenko.otus.moviesearcher.data.source.MoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

class LocalMoviesDataSource(private val movieMapper: LocalToDomainMovieMapper) : MoviesDataSource {

    override fun getMovies(): List<MovieEntity> {
        return movieMapper.mapLocalMovieDtoToMovieEntity(LocalMovieStorage.movies)
    }
}