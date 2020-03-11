package io.vasilenko.otus.moviesearcher.presentation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.vasilenko.otus.moviesearcher.core.presentation.BaseViewModel
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

class FavoriteMoviesViewModel(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : BaseViewModel() {

    private var _movies: MutableLiveData<List<MovieModel>> = MutableLiveData()

    val movies: LiveData<List<MovieModel>?> get() = _movies

    fun loadFavoriteMovies() {
        val movies = movieInteractor.searchFavoriteMovies()
        _movies.postValue(mapper.mapMovieEntitiesToModels(movies))
    }

    fun deleteFromFavorites(movie: MovieModel) {
        movieInteractor.removeMovieFromFavorites(mapper.mapMovieModelToEntity(movie))
    }
}