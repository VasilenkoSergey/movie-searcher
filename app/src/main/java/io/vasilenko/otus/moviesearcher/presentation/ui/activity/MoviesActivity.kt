package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import io.vasilenko.otus.moviesearcher.MovieSearcherApp.Companion.moviesPresenter
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.MoviesContract
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.ui.dialog.QuitDialog
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesActivity : AppCompatActivity(),
    MoviesContract.View {

    private val visitedMovies = mutableListOf<Int>()

    companion object {
        const val TAG = "MoviesActivity"
        const val EMPTY_RESOURCE_ID = 0
        const val MOVIE_REQUEST_CODE = 0
    }

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
        dialog.setOnCancelListener {
            super.onBackPressed()
        }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MOVIE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val comment = data?.getStringExtra("comment")
            val isLiked = data?.getBooleanExtra("isLiked", false)
            Log.i(TAG, "comment: $comment is liked: $isLiked")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("visitedMovies", visitedMovies.toIntArray())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val visitedItems = savedInstanceState.getIntArray("visitedMovies")
        visitedItems?.forEach {
            visitedMovies.add(it)
            setMovieSelectedState(movieItems.getChildAt(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getMovies() {
        presenter.loadMovies()
    }

    override fun showMovies(movies: List<MovieModel>) {
        movies.forEach { addMovieItem(it) }
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

    private fun setMovieSelectedState(item: View) {
        item.movieTitle.setTextColor(
            ContextCompat.getColor(
                this@MoviesActivity,
                R.color.colorPrimary
            )
        )
    }

    //временное решение, т.к. мы еще не используем recycler
    private fun addMovieItem(movieModel: MovieModel) {
        val movieItemView = prepareItemView(movieModel, position = movieItems.childCount)
        movieItems.addView(movieItemView)
    }

    @SuppressLint("InflateParams")
    private fun prepareItemView(movieModel: MovieModel, position: Int): View {
        movieModel.imgId = applicationContext.resources.getIdentifier(
            movieModel.imageName,
            "drawable",
            packageName
        )
        val movieItemView = layoutInflater.inflate(R.layout.movie_item, null, false)

        movieItemView.movieTitle.text = movieModel.title
        movieItemView.movieRating.text = movieModel.rating
        movieItemView.movieImg.setImageResource(
            if (movieModel.imgId != EMPTY_RESOURCE_ID) movieModel.imgId else R.drawable.movie_default
        )

        movieItemView.movieDetail.setOnClickListener {
            setMovieSelectedState(movieItemView.movieTitle)
            visitedMovies.add(position)
            startActivityForResult(
                Intent(this, MovieActivity::class.java).apply {
                    putExtra(
                        "movie",
                        movieModel
                    )
                },
                MOVIE_REQUEST_CODE
            )
        }
        return movieItemView
    }
}
