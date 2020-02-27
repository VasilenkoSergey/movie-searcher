package io.vasilenko.otus.moviesearcher.presentation.presenter.impl

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.presenter.TopMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

class TopMoviesPresenterImpl(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : BasePresenterImpl<TopMoviesView>(), TopMoviesPresenter,
    MovieInteractor.TopMoviesSearchListener, MovieInteractor.NextTopMoviesSearchListener {

    private var currentPage = INITIAL_PAGE
    private var position = INITIAL_POSTITION

    override fun onViewCreated() {
        loadTopMovies()
    }

    override fun loadTopMovies() {
        view?.showLoading(true)
        movieInteractor.searchTopMovies(listener = this)
    }

    override fun loadNextTopMovies() {
        currentPage++
        view?.showLoading(true)
        movieInteractor.searchNextTopMovies(listener = this, page = currentPage)
    }

    override fun reloadTopMovies() {
        currentPage = INITIAL_PAGE
        position = INITIAL_POSTITION
        view?.showLoading(true)
        movieInteractor.reloadTopMovies(listener = this)
    }

    override fun onScrollTopMovies() {
        loadNextTopMovies()
    }

    override fun onRefreshTopMovies() {
        reloadTopMovies()
    }

    override fun onDestroyView(position: Int) {
        this.position = position
    }

    override fun addMovieToFavorites(movieModel: MovieModel) {
        val movie = mapper.mapMovieModelToEntity(movieModel)
        val isFavorite = movieInteractor.isMovieFavorite(movie)
        if (isFavorite) {
            view?.showMessageIfMovieExistInFavorites()
        } else {
            movieInteractor.addMovieToFavorites(movie)
            view?.showMessageOnSuccessfulAddingToFavorites(movieModel)
        }
    }

    override fun deleteFromFavorites(movie: MovieModel) {
        movieInteractor.removeMovieFromFavorites(mapper.mapMovieModelToEntity(movie))
    }

    override fun onSearchFinished(movies: List<MovieEntity>) {
        view?.showTopMovies(mapper.mapMovieEntitiesToModels(movies))
        view?.scrollToPosition(position)
        view?.showLoading(false)
    }

    override fun onSearchFailure(t: Throwable?) {
        t?.message?.let { view?.showErrorMessage(it) }
    }

    override fun onNextSearchFinished(movies: List<MovieEntity>) {
        view?.updateTopMovies(mapper.mapMovieEntitiesToModels(movies))
        view?.showLoading(false)
    }

    override fun onNextSearchFailure(t: Throwable?) {
        t?.message?.let { view?.showErrorMessage(it) }
    }

    private companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_POSTITION = 0
    }
}