package io.vasilenko.otus.moviesearcher.presentation.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper

class TopMoviesVMFactory(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopMoviesViewModel(
            movieInteractor,
            mapper
        ) as T
    }
}