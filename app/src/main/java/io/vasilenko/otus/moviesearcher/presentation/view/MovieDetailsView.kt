package io.vasilenko.otus.moviesearcher.presentation.view

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

interface MovieDetailsView {

    fun showMovie(movie: MovieModel)
}