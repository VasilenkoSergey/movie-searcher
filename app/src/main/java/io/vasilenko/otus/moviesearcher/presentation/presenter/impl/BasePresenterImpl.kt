package io.vasilenko.otus.moviesearcher.presentation.presenter.impl

import io.vasilenko.otus.moviesearcher.presentation.presenter.BasePresenter
import io.vasilenko.otus.moviesearcher.presentation.view.BaseView

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}