package io.vasilenko.otus.moviesearcher.presentation.ui.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.vasilenko.otus.moviesearcher.core.presentation.BaseViewModel
import io.vasilenko.otus.moviesearcher.core.presentation.SingleLiveEvent
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

class TopMoviesViewModel(
    private val movieInteractor: MovieInteractor,
    private val mapper: MovieModelMapper
) : BaseViewModel() {

    private var _viewState: MutableLiveData<TopMoviesViewState> = MutableLiveData()
    private var _messageState: SingleLiveEvent<Message> = SingleLiveEvent()

    init {
        _viewState.value = TopMoviesViewState()
    }

    val viewState: LiveData<TopMoviesViewState> get() = _viewState
    val messageState: LiveData<Message?> get() = _messageState

    fun onViewCreated() {
        addDisposable(movieInteractor.searchTopMovies()
            .doOnSubscribe {
                _viewState.value = currentViewState().copy(isLoading = true)
            }
            .subscribe({ movies ->
                _viewState.value = currentViewState().copy(
                    isLoading = false,
                    movies = mapper.mapMovieEntitiesToModels(movies)
                )
            }, {
                _viewState.value = currentViewState().copy(isLoading = false)
                it?.message?.let { msg -> _messageState.value = Message.LoadError(msg) }
            })
        )
    }

    fun onScrollTopMovies() {
        val currentPage = (currentViewState().currentPage).plus(1)

        addDisposable(movieInteractor.searchNextTopMovies(page = currentPage)
            .doOnSubscribe {
                _viewState.value =
                    currentViewState().copy(
                        isLoading = true,
                        movies = null
                    )
            }
            .subscribe({
                _viewState.value = currentViewState().copy(
                    isLoading = false,
                    currentPage = currentPage,
                    movies = mapper.mapMovieEntitiesToModels(it)
                )
            }, {
                _viewState.value = currentViewState().copy(isLoading = false)
                it?.message?.let { msg -> _messageState.value = Message.LoadNextError(msg) }
            })
        )
    }

    fun onRefreshTopMovies() {
        val currentPage = 1
        addDisposable(movieInteractor.reloadTopMovies()
            .doOnSubscribe {
                _viewState.value =
                    currentViewState().copy(
                        isLoading = true,
                        currentPage = currentPage,
                        movies = null
                    )
            }
            .subscribe({
                _viewState.value = currentViewState().copy(
                    isLoading = false,
                    movies = mapper.mapMovieEntitiesToModels(it)
                )
            }, {
                _viewState.value = currentViewState().copy(isLoading = false)
                it?.message?.let { msg -> _messageState.value = Message.RefreshError(msg) }
            })
        )
    }

    fun addMovieToFavorites(movieModel: MovieModel) {
        val movie = mapper.mapMovieModelToEntity(movieModel)
        val isFavorite = movieInteractor.isMovieFavorite(movie)
        if (isFavorite) {
            _messageState.value = Message.FavoriteExist
        } else {
            movieInteractor.addMovieToFavorites(movie)
            _messageState.value = Message.AddToFavorite(movieModel)
        }
    }

    fun deleteFromFavorites(movie: MovieModel) {
        movieInteractor.removeMovieFromFavorites(mapper.mapMovieModelToEntity(movie))
    }

    fun retry(message: Message) {
        when (message) {
            is Message.LoadError -> onViewCreated()
            is Message.LoadNextError -> onScrollTopMovies()
            is Message.RefreshError -> onRefreshTopMovies()
        }
    }

    private fun currentViewState(): TopMoviesViewState = _viewState.value!!
}