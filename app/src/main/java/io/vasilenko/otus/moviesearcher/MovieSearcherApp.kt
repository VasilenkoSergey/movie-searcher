package io.vasilenko.otus.moviesearcher

import android.app.Application
import io.vasilenko.otus.moviesearcher.data.mapper.LocalToDomainMovieMapper
import io.vasilenko.otus.moviesearcher.data.repo.MoviesRepoImpl
import io.vasilenko.otus.moviesearcher.data.source.local.LocalMoviesDataSource
import io.vasilenko.otus.moviesearcher.data.source.remote.RemoteMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractorImpl
import io.vasilenko.otus.moviesearcher.presentation.mapper.MovieModelMapper
import io.vasilenko.otus.moviesearcher.presentation.presenter.impl.TopMoviesPresenterImpl

class MovieSearcherApp : Application() {

    //временное решение, т.к. мы еще не используем di
    companion object {
        private val localToDomainMovieMapper = LocalToDomainMovieMapper()
        private val localDataSource = LocalMoviesDataSource(localToDomainMovieMapper)
        private val remoteDataSource = RemoteMoviesDataSource()
        private val repo = MoviesRepoImpl(localDataSource, remoteDataSource)
        private val interactor = MovieInteractorImpl(repo)
        private val movieModelMapper = MovieModelMapper()
        val topMoviesPresenter =
            TopMoviesPresenterImpl(
                interactor,
                movieModelMapper
            )
    }
}