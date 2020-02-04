package io.vasilenko.otus.moviesearcher.presentation.presenter.impl

import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.presenter.FavoriteMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.view.FavoriteMoviesView

class FavoriteMoviesPresenterImpl(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : BasePresenterImpl<FavoriteMoviesView>(), FavoriteMoviesPresenter {

    override fun loadFavoriteMovies() {
        val movies = movieInteractor.searchFavoriteMovies()
        view?.showFavoriteMovies(mapper.mapMovieEntitiesToModels(movies))
    }

    override fun deleteFromFavorites(movie: MovieModel) {
        movieInteractor.removeMovieFromFavorites(mapper.mapMovieModelToEntity(movie))
    }
}