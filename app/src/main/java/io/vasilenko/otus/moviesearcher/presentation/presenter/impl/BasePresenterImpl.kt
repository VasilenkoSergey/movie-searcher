package io.vasilenko.otus.moviesearcher.presentation.presenter.impl

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.vasilenko.otus.moviesearcher.presentation.presenter.BasePresenter
import io.vasilenko.otus.moviesearcher.presentation.view.BaseView

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var view: V? = null
    lateinit var compositeDisposable: CompositeDisposable

    override fun attachView(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}