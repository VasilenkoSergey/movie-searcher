package io.vasilenko.otus.moviesearcher.presentation.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

interface MoviesRouter {

    fun setRouterHandler(routerHandler: MoviesRouterHandler)

    fun onOpenActivity(intent: Intent)

    fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean)
}