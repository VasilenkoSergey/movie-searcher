package io.vasilenko.otus.moviesearcher.presentation.navigation.impl

import android.content.Intent
import androidx.fragment.app.Fragment
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouterHandler

class MoviesRouterImpl : MoviesRouter {

    private var routerHandler: MoviesRouterHandler? = null

    override fun setRouterHandler(routerHandler: MoviesRouterHandler) {
        this.routerHandler = routerHandler
    }

    override fun onOpenActivity(intent: Intent) {
        routerHandler?.onOpenActivity(intent)
    }

    override fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean) {
        routerHandler?.onOpenFragment(fragment, addToBackStack)
    }
}