package io.vasilenko.otus.moviesearcher.presentation.view

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

interface TopMoviesView : BaseView {

    fun showLoading(state: Boolean)

    fun showTopMovies(movies: List<MovieModel>)

    fun updateTopMovies(movies: List<MovieModel>)

    fun showMessageOnSuccessfulAddingToFavorites(movie: MovieModel)

    fun showMessageIfMovieExistInFavorites()

    fun showErrorMessage(message: String)

    fun scrollToPosition(position: Int)
}