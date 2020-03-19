package io.vasilenko.otus.moviesearcher.presentation.view

import io.vasilenko.otus.moviesearcher.core.presentation.BaseView
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.ui.top.Message

interface TopMoviesView : BaseView {

    fun showLoading(state: Boolean)

    fun showTopMovies(movies: List<MovieModel>)

    fun updateTopMovies(movies: List<MovieModel>)

    fun showMessageOnSuccessfulAddingToFavorites(movie: MovieModel)

    fun showMessageIfMovieExistInFavorites()

    fun showErrorMessage(message: Message)

    fun scrollToPosition(position: Int)
}