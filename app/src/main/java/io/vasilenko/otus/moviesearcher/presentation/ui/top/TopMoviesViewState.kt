package io.vasilenko.otus.moviesearcher.presentation.ui.top

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

data class TopMoviesViewState(
    var isLoading: Boolean = true,
    var currentPage: Int = 1,
    var movies: List<MovieModel>? = null
)

sealed class Message {
    class LoadError(val msg: String) : Message()
    class LoadNextError(val msg: String) : Message()
    class RefreshError(val msg: String) : Message()
    class AddToFavorite(val movie: MovieModel) : Message()
    object FavoriteExist : Message()
}