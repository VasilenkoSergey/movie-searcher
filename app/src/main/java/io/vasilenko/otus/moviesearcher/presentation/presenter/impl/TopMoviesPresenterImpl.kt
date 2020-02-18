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
    MovieInteractor.TopMoviesSearchListener {

    private var currentPage = INITIAL_PAGE

    override fun onViewCreated() {
        loadTopMovies()
    }

    override fun loadTopMovies() {
        currentPage = INITIAL_PAGE
        view?.setLoadingState(true)
        movieInteractor.searchTopMovies(this, currentPage)
    }

    override fun loadNextTopMovies() {
        currentPage++
        view?.setLoadingState(true)
        movieInteractor.searchTopMovies(this, currentPage)
    }

    override fun onScrollTopMovies() {
        loadNextTopMovies()
    }

    override fun onRefreshTopMovies() {
        loadTopMovies()
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
        if (currentPage == INITIAL_PAGE) {
            view?.showTopMovies(mapper.mapMovieEntitiesToModels(movies))
        } else {
            view?.updateTopMovies(mapper.mapMovieEntitiesToModels(movies))
        }
        view?.setLoadingState(false)
    }

    override fun onSearchFailure(t: Throwable?) {
        t?.message?.let { view?.showErrorMessage(it) }
    }

    private companion object {
        const val INITIAL_PAGE = 1
    }
}