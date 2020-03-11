package io.vasilenko.otus.moviesearcher.presentation.view

import io.vasilenko.otus.moviesearcher.core.presentation.BaseView

interface MoviesView : BaseView {

    fun showTopMovies()

    fun showFavoriteMovies()
}