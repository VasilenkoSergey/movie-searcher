package io.vasilenko.otus.moviesearcher.presentation.navigation

import androidx.fragment.app.Fragment

interface MoviesRouterHandler {

    fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean)
}