package io.vasilenko.otus.moviesearcher.presentation.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

interface MoviesRouterHandler {

    fun onOpenActivity(intent: Intent)

    fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean)
}