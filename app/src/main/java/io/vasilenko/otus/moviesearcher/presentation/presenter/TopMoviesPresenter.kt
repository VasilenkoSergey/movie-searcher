package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

interface TopMoviesPresenter : BasePresenter<TopMoviesView> {

    fun onViewCreated()

    fun onScrollTopMovies()

    fun onRefreshTopMovies()

    fun onDestroyView(position: Int)

    fun addMovieToFavorites(movieModel: MovieModel)

    fun deleteFromFavorites(movie: MovieModel)
}