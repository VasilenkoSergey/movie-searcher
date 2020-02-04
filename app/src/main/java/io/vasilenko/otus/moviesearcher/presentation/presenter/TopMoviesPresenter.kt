package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

interface TopMoviesPresenter : BasePresenter<TopMoviesView> {

    fun loadTopMovies()

    fun addMovieToFavorites(movieModel: MovieModel)
}