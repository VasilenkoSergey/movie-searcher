package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import io.vasilenko.otus.moviesearcher.MovieSearcherApp.Companion.moviesPresenter
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.MoviesContract
import io.vasilenko.otus.moviesearcher.presentation.decoration.MovieItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.adapter.MovieItemAdapter
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.ui.dialog.QuitDialog
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    lateinit var presenter: MoviesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        presenter = moviesPresenter
        presenter.attachView(this@MoviesActivity)
        getMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.movies_invite -> {
                invite()
                true
            }
            R.id.theme_change -> {
                changeTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val dialog = QuitDialog(this@MoviesActivity)
        dialog.setOnCancelListener { super.onBackPressed() }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getMovies() {
        presenter.loadMovies()
    }

    override fun showMovies(movies: List<MovieModel>) {
        movieItemsRv.layoutManager = GridLayoutManager(this@MoviesActivity, 2)
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        movieItemsRv.addItemDecoration(
            MovieItemDecoration(
                padding
            )
        )
        movieItemsRv.adapter = MovieItemAdapter(movies) { movie -> listener(movie) }
    }

    private fun listener(movie: MovieModel) {
        startActivity(Intent(this@MoviesActivity, MovieActivity::class.java).apply {
            putExtra("movie", movie)
        })
    }

    private fun invite() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.movie_invite_text))
        }
        intent.resolveActivity(packageManager)?.let {
            startActivity(intent)
        }
    }

    private fun changeTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
