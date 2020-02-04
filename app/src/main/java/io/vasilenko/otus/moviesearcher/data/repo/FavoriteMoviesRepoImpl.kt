package io.vasilenko.otus.moviesearcher.data.repo

import io.vasilenko.otus.moviesearcher.data.source.FavoritesMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.repo.FavoriteMoviesRepo

class FavoriteMoviesRepoImpl(private val favoritesMoviesDataSource: FavoritesMoviesDataSource) :
    FavoriteMoviesRepo {

    override fun getAllMovies(): List<MovieEntity> {
        return favoritesMoviesDataSource.getAllMovies()
    }

    override fun existMovieByEntity(movie: MovieEntity): Boolean {
        return favoritesMoviesDataSource.existMovieByEntity(movie)
    }

    override fun addMovie(movie: MovieEntity) {
        favoritesMoviesDataSource.addMovie(movie)
    }

    override fun delMovie(movie: MovieEntity) {
        favoritesMoviesDataSource.delMovie(movie)
    }

}