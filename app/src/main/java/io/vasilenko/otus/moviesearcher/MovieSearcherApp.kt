package io.vasilenko.otus.moviesearcher

import android.app.Application
import io.vasilenko.otus.moviesearcher.data.mapper.LocalMovieMapper
import io.vasilenko.otus.moviesearcher.data.network.NetworkProvider
import io.vasilenko.otus.moviesearcher.data.repo.FavoriteMoviesRepoImpl
import io.vasilenko.otus.moviesearcher.data.repo.TopMoviesRepoImpl
import io.vasilenko.otus.moviesearcher.data.source.local.LocalFavoriteMoviesDataSource
import io.vasilenko.otus.moviesearcher.data.source.local.LocalTopMoviesDataSource
import io.vasilenko.otus.moviesearcher.data.source.remote.RemoteTopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractorImpl
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.navigation.impl.MoviesRouterImpl
import io.vasilenko.otus.moviesearcher.presentation.presenter.impl.FavoriteMoviesPresenterImpl
import io.vasilenko.otus.moviesearcher.presentation.presenter.impl.TopMoviesPresenterImpl

class MovieSearcherApp : Application() {

    //временное решение, т.к. мы еще не используем di
    companion object {
        private val localMovieMapper = LocalMovieMapper()
        private val topMoviesLocalDataSource = LocalTopMoviesDataSource(localMovieMapper)
        private val topMoviesRemoteDataSource = RemoteTopMoviesDataSource(NetworkProvider.api(), localMovieMapper)
        private val topMoviesRepo =
            TopMoviesRepoImpl(topMoviesLocalDataSource, topMoviesRemoteDataSource)
        private val favoriteMoviesLocalDataSource =
            LocalFavoriteMoviesDataSource(localMovieMapper)
        private val favoriteMoviesRepo = FavoriteMoviesRepoImpl(favoriteMoviesLocalDataSource)
        private val interactor = MovieInteractorImpl(topMoviesRepo, favoriteMoviesRepo)
        private val movieModelMapper = MovieModelMapper()
        val topMoviesPresenter = TopMoviesPresenterImpl(interactor, movieModelMapper)
        val favoriteMoviesPresenter = FavoriteMoviesPresenterImpl(interactor, movieModelMapper)
        val router = MoviesRouterImpl()
    }
}