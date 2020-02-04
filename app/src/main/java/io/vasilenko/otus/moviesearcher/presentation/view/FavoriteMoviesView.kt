package io.vasilenko.otus.moviesearcher.presentation.view

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

interface FavoriteMoviesView : BaseView {

    fun getFavoriteMovies()

    fun showFavoriteMovies(movies: List<MovieModel>)
}