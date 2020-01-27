package io.vasilenko.otus.moviesearcher.domain.interaction

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.repo.MoviesRepo

class MovieInteractorImpl(private val moviesRepo: MoviesRepo) : MovieInteractor {

    override fun searchMovies(): List<MovieEntity> {
        return moviesRepo.getAllMovies()
    }
}