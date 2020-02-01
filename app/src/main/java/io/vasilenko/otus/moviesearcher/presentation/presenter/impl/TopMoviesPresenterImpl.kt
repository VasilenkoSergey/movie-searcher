package io.vasilenko.otus.moviesearcher.presentation.presenter.impl

import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.presenter.TopMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

class TopMoviesPresenterImpl(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : TopMoviesPresenter {

    private var view: TopMoviesView? = null

    override fun attachView(view: TopMoviesView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadTopMovies() {
        val movies = movieInteractor.searchMovies()
        view?.showMovies(mapper.mapMovieEntitiesToModels(movies))
    }
}