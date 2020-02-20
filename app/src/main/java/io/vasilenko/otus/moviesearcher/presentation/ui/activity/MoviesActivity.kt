package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.common.MessageBundle
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouterHandler
import io.vasilenko.otus.moviesearcher.presentation.ui.dialog.QuitDialog
import io.vasilenko.otus.moviesearcher.presentation.ui.fragment.FavoriteMoviesFragment
import io.vasilenko.otus.moviesearcher.presentation.ui.fragment.TopMoviesFragment
import io.vasilenko.otus.moviesearcher.presentation.view.MoviesView
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(), MoviesView, MoviesRouterHandler {

    lateinit var router: MoviesRouter

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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val dialog = QuitDialog(this@MoviesActivity)
            dialog.setOnCancelListener { super.onBackPressed() }
            dialog.show()
        }
    }

    override fun showTopMovies() {
        onOpenFragment(TopMoviesFragment(), addToBackStack = false)
    }

    override fun showFavoriteMovies() {
        onOpenFragment(FavoriteMoviesFragment(), addToBackStack = false)
    }

    override fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean) {
        val tag = fragment.javaClass.name
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.moviesContainer, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.name).commit()
        } else {
            transaction.commit()
        }
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
}