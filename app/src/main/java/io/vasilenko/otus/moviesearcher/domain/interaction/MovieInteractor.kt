package io.vasilenko.otus.moviesearcher.domain.interaction

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

interface MovieInteractor {

    fun searchMovies(): List<MovieEntity>
}