package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import io.vasilenko.otus.moviesearcher.MovieSearcherApp.Companion.topMoviesPresenter
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.presenter.TopMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.ui.adapter.TopMoviesAdapter
import io.vasilenko.otus.moviesearcher.presentation.ui.decoration.MovieItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.ui.dialog.QuitDialog
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView
import kotlinx.android.synthetic.main.activity_top_movies.*

class TopMoviesActivity : AppCompatActivity(), TopMoviesView {

    lateinit var presenter: TopMoviesPresenter

    private lateinit var topMoviesAdapter: TopMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_movies)
        setSupportActionBar(moviesToolBar)
        presenter = topMoviesPresenter
        presenter.attachView(this@TopMoviesActivity)
        setupViews()
        getTopMovies()
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
            R.id.favorite_movies -> {
                showFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val dialog = QuitDialog(this@TopMoviesActivity)
        dialog.setOnCancelListener { super.onBackPressed() }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getTopMovies() {
        presenter.loadTopMovies()
    }

    override fun showTopMovies(movies: List<MovieModel>) {
        topMoviesAdapter.setMovies(movies)
    }

    override fun showMessageOnSuccessfulAddingToFavorites() {
        Toast.makeText(
            this@TopMoviesActivity,
            getString(R.string.added_to_favorites),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showMessageIfMovieExistInFavorites() {
        Toast.makeText(
            this@TopMoviesActivity,
            getString(R.string.already_favorite),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupViews() {
        topMoviesAdapter = TopMoviesAdapter(
            { movie -> movieOpenClickListener(movie) },
            { movie -> movieAddToFavoriteClickListener(movie) }
        )
        topMoviesRv.layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                GridLayoutManager(this@TopMoviesActivity, PORTRAIT_SPAN_COUNT)
            else ->
                GridLayoutManager(this@TopMoviesActivity, LANDSCAPE_SPAN_COUNT)
        }
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        topMoviesRv.addItemDecoration(
            MovieItemDecoration(
                padding
            )
        )
        topMoviesRv.adapter = topMoviesAdapter
    }

    private fun movieOpenClickListener(movie: MovieModel) {
        startActivity(Intent(this@TopMoviesActivity, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        })
    }

    private fun movieAddToFavoriteClickListener(movie: MovieModel) {
        presenter.addMovieToFavorites(movie)
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
        when (AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.getDefaultNightMode() -> AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun showFavorites() {
        startActivity(Intent(this@TopMoviesActivity, FavoriteMoviesActivity::class.java))
    }

    private companion object {
        const val PORTRAIT_SPAN_COUNT = 2
        const val LANDSCAPE_SPAN_COUNT = 3
    }
}
