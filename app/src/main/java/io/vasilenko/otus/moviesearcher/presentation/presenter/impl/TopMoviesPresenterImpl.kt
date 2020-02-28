package io.vasilenko.otus.moviesearcher.presentation.presenter.impl

import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.presenter.TopMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

class TopMoviesPresenterImpl(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : BasePresenterImpl<TopMoviesView>(), TopMoviesPresenter {

    private var currentPage = INITIAL_PAGE
    private var position = INITIAL_POSITION

    override fun onViewCreated() {
        addDisposable(movieInteractor.searchTopMovies()
            .doOnSubscribe {
                view?.showLoading(state = true)
            }
            .subscribe({
                view?.showTopMovies(mapper.mapMovieEntitiesToModels(it))
                view?.scrollToPosition(position)
                view?.showLoading(false)
            }, {
                it?.message?.let { msg -> view?.showErrorMessage(msg) }
            })
        )
    }

    override fun onScrollTopMovies() {
        currentPage++
        addDisposable(movieInteractor.searchNextTopMovies(page = currentPage)
            .doOnSubscribe {
                view?.showLoading(state = true)
            }
            .subscribe({
                view?.updateTopMovies(mapper.mapMovieEntitiesToModels(it))
                view?.showLoading(state = false)
            }, {
                it?.message?.let { msg -> view?.showErrorMessage(msg) }
            })
        )
    }

    override fun onRefreshTopMovies() {
        currentPage = INITIAL_PAGE
        position = INITIAL_POSITION
        addDisposable(movieInteractor.reloadTopMovies()
            .doOnSubscribe {
                view?.showLoading(state = true)
            }
            .subscribe({
                view?.showTopMovies(mapper.mapMovieEntitiesToModels(it))
                view?.scrollToPosition(position)
                view?.showLoading(state = false)
            }, {
                it?.message?.let { msg -> view?.showErrorMessage(msg) }
            })
        )
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

    private companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_POSITION = 0
    }
}