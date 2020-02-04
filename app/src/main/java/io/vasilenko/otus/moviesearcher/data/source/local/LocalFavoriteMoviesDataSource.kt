package io.vasilenko.otus.moviesearcher.data.source.local

import io.vasilenko.otus.moviesearcher.data.local.LocalFavoriteMovieStorage
import io.vasilenko.otus.moviesearcher.data.mapper.LocalToDomainMovieMapper
import io.vasilenko.otus.moviesearcher.data.source.FavoritesMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

class LocalFavoriteMoviesDataSource(private val mapper: LocalToDomainMovieMapper) :
    FavoritesMoviesDataSource {

    override fun getAllMovies(): List<MovieEntity> {
        return mapper.mapLocalMovieDtoToMovieEntity(LocalFavoriteMovieStorage.movies.toList())
    }

    override fun existMovieByEntity(movie: MovieEntity): Boolean {
        return LocalFavoriteMovieStorage.movies.contains(mapper.mapMovieEntityToLocalMovie(movie))
    }

    override fun addMovie(movie: MovieEntity) {
        LocalFavoriteMovieStorage.movies.add(mapper.mapMovieEntityToLocalMovie(movie))
    }

    override fun delMovie(movie: MovieEntity) {
        LocalFavoriteMovieStorage.movies.remove(mapper.mapMovieEntityToLocalMovie(movie))
    }
}