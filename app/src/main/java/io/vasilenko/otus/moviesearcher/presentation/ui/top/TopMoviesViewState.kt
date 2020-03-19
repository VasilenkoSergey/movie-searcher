package io.vasilenko.otus.moviesearcher.presentation.ui.top

import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

data class TopMoviesViewState(
    var isLoading: Boolean = true,
    var currentPage: Int = 1,
    var movies: List<MovieModel>? = null
)

sealed class Message {
    object LoadError : Message()
    object LoadNextError : Message()
    object RefreshError : Message()
    class AddToFavorite(val movie: MovieModel) : Message()
    object FavoriteExist : Message()
}