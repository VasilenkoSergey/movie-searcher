package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.content.Intent
import android.content.res.Configuration
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
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.ui.adapter.MoviesListAdapter
import io.vasilenko.otus.moviesearcher.presentation.ui.dialog.QuitDialog
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesListActivity : AppCompatActivity(), MoviesContract.View {

    lateinit var presenter: MoviesContract.Presenter

    private lateinit var moviesListAdapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        presenter = moviesPresenter
        presenter.attachView(this@MoviesListActivity)
        setupViews()
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
        val dialog = QuitDialog(this@MoviesListActivity)
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
        moviesListAdapter.setMovies(movies)
    }

    private fun setupViews() {
        moviesListAdapter = MoviesListAdapter { movie -> listener(movie) }
        movieItemsRv.layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                GridLayoutManager(this@MoviesListActivity, PORTRAIT_SPAN_COUNT)
            else ->
                GridLayoutManager(this@MoviesListActivity, LANDSCAPE_SPAN_COUNT)
        }
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        movieItemsRv.addItemDecoration(MovieItemDecoration(padding))
        movieItemsRv.adapter = moviesListAdapter
    }

    private fun listener(movie: MovieModel) {
        startActivity(Intent(this@MoviesListActivity, MovieDetailActivity::class.java).apply {
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
        when (AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.getDefaultNightMode() -> AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    companion object {
        const val PORTRAIT_SPAN_COUNT = 2
        const val LANDSCAPE_SPAN_COUNT = 3
    }
}
