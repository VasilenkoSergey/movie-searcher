package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.view.FavoriteMoviesView

interface FavoriteMoviesPresenter : BasePresenter<FavoriteMoviesView> {

    fun loadFavoriteMovies()

    fun deleteFromFavorites(movie: MovieModel)
}