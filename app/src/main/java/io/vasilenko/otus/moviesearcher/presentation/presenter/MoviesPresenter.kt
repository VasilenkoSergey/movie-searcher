package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.MoviesContract
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper

class MoviesPresenter(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) :
    MoviesContract.Presenter {

    private var view: MoviesContract.View? = null

    override fun attachView(view: MoviesContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadMovies() {
        val movies = movieInteractor.searchMovies()
        view?.showMovies(mapper.mapMovieEntitiesToModels(movies))
    }
}