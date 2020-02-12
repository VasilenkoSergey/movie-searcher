package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
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
        val dialog = QuitDialog(this@MoviesActivity)
        dialog.setOnCancelListener { super.onBackPressed() }
        dialog.show()
    }

    override fun showTopMovies() {
        onOpenFragment(TopMoviesFragment(), addToBackStack = false)
    }

    override fun showFavoriteMovies() {
        onOpenFragment(FavoriteMoviesFragment(), addToBackStack = false)
    }

    override fun onOpenActivity(intent: Intent) {
        this@MoviesActivity.startActivity(intent)
    }

    override fun onOpenFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.moviesContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.name).commit()
        } else {
            transaction.commit()
        }
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