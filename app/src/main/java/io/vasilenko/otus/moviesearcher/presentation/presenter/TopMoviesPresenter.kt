package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

interface TopMoviesPresenter : BasePresenter<TopMoviesView> {

    fun onViewCreated()

    fun loadTopMovies()

    fun loadNextTopMovies()

    fun onScrollTopMovies()

    fun onRefreshTopMovies()

    fun addMovieToFavorites(movieModel: MovieModel)

    fun deleteFromFavorites(movie: MovieModel)
}