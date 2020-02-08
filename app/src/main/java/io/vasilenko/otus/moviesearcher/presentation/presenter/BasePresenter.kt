package io.vasilenko.otus.moviesearcher.presentation.presenter

import io.vasilenko.otus.moviesearcher.presentation.view.BaseView

interface BasePresenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}