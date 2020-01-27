package io.vasilenko.otus.moviesearcher.presentation

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

interface MoviesContract {

    interface View {

        fun getMovies()

        fun showMovies(movies: List<MovieModel>)
    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun loadMovies()
    }
}