package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView

interface BasePresenter {

    fun attachView(view: TopMoviesView)

    fun detachView()
}