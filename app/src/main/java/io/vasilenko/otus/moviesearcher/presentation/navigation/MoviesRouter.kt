package io.vasilenko.otus.moviesearcher.presentation.navigation

import androidx.fragment.app.Fragment

interface MoviesRouter {

    fun setRouterHandler(routerHandler: MoviesRouterHandler)

    fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean, showNavBar: Boolean)
}