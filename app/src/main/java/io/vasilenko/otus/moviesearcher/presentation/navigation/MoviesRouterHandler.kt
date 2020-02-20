package io.vasilenko.otus.moviesearcher.presentation.navigation

import androidx.fragment.app.Fragment
import io.vasilenko.otus.moviesearcher.presentation.common.MessageBundle

interface MoviesRouterHandler {

    fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean)

    fun onMessage(messageBundle: MessageBundle)
}