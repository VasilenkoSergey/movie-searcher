package io.vasilenko.otus.moviesearcher.presentation.view

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

interface TopMoviesView : BaseView {

    fun getTopMovies()

    fun showTopMovies(movies: List<MovieModel>)

    fun showMessageOnSuccessfulAddingToFavorites()

    fun showMessageIfMovieExistInFavorites()
}