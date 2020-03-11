package io.vasilenko.otus.moviesearcher.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.common.MessageBundle
import io.vasilenko.otus.moviesearcher.presentation.common.QuitDialog
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouterHandler
import io.vasilenko.otus.moviesearcher.presentation.ui.favorite.FavoriteMoviesFragment
import io.vasilenko.otus.moviesearcher.presentation.ui.top.TopMoviesFragment
import io.vasilenko.otus.moviesearcher.presentation.view.MoviesView
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(), MoviesView, MoviesRouterHandler {

    lateinit var router: MoviesRouter

    private val top: TopMoviesFragment by lazy { TopMoviesFragment() }
    private val favorite: FavoriteMoviesFragment by lazy { FavoriteMoviesFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        router = MovieSearcherApp.router
        router.setRouterHandler(this)
        moviesBottomNavigation.setOnNavigationItemSelectedListener { item -> navBarListener(item) }
        if (savedInstanceState == null) {
            showTopMovies()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NAV_BAR_VISIBILITY_KEY, moviesBottomNavigation.visibility)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val navBarVisibility = savedInstanceState.getInt(NAV_BAR_VISIBILITY_KEY, View.VISIBLE)
        moviesBottomNavigation.visibility = navBarVisibility
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            moviesBottomNavigation.visibility = View.VISIBLE
            supportFragmentManager.popBackStack()
        } else {
            val dialog =
                QuitDialog(
                    this@MoviesActivity
                )
            dialog.setOnCancelListener { super.onBackPressed() }
            dialog.show()
        }
    }

    override fun showTopMovies() {
        onOpenFragment(top, addToBackStack = false, showNavBar = true)
    }

    override fun showFavoriteMovies() {
        onOpenFragment(favorite, addToBackStack = false, showNavBar = true)
    }

    override fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean, showNavBar: Boolean) {
        val tag = fragment.javaClass.name
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.moviesContainer, fragment, tag)
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)
        if (!showNavBar) moviesBottomNavigation.visibility = View.GONE
        transaction.commit()
    }

    override fun onMessage(messageBundle: MessageBundle) {
        val snackbar = Snackbar.make(
            requireViewById(R.id.moviesContainer),
            messageBundle.text,
            Snackbar.LENGTH_SHORT
        )
        messageBundle.action?.let {
            snackbar.setAction(messageBundle.action.name, messageBundle.action.listener).show()
        }
        snackbar.show()
    }

    private fun navBarListener(item: MenuItem): Boolean {
        if (item.itemId == moviesBottomNavigation.selectedItemId) {
            return false
        }
        when (item.itemId) {
            R.id.topMoviesMenu -> showTopMovies()
            R.id.favoriteMoviesMenu -> showFavoriteMovies()
        }
        return true
    }

    private companion object {
        const val NAV_BAR_VISIBILITY_KEY = "NAV_BAR_VISIBILITY_KEY"
    }
}