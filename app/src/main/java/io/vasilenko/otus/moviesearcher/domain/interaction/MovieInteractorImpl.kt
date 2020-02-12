package io.vasilenko.otus.moviesearcher.domain.interaction

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.repo.FavoriteMoviesRepo
import io.vasilenko.otus.moviesearcher.domain.repo.TopMoviesRepo

class MovieInteractorImpl(
    private val topMoviesRepo: TopMoviesRepo,
    private val favoriteMoviesRepo: FavoriteMoviesRepo
) : MovieInteractor {

    override fun searchTopMovies(listener: MovieInteractor.TopMoviesSearchListener, page: Int): List<MovieEntity> {
        return topMoviesRepo.getAllMovies(listener, page)
    }

    override fun searchFavoriteMovies(): List<MovieEntity> {
        return favoriteMoviesRepo.getAllMovies()
    }

    override fun isMovieFavorite(movieEntity: MovieEntity): Boolean {
        return favoriteMoviesRepo.existMovieByEntity(movieEntity)
    }

    override fun addMovieToFavorites(movieEntity: MovieEntity) {
        favoriteMoviesRepo.addMovie(movieEntity)
    }

    override fun removeMovieFromFavorites(movieEntity: MovieEntity) {
        favoriteMoviesRepo.delMovie(movieEntity)
    }
}