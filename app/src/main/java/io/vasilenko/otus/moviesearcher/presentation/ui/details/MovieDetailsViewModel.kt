package io.vasilenko.otus.moviesearcher.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.vasilenko.otus.moviesearcher.core.presentation.BaseViewModel
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

class MovieDetailsViewModel : BaseViewModel() {

    private var _movie: MutableLiveData<MovieModel> = MutableLiveData()

    val movie: LiveData<MovieModel> get() = _movie

    fun setDetails(movieModel: MovieModel) {
        _movie.value = movieModel
    }
}